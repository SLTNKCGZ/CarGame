package application;

import java.io.File;
import java.io.FileNotFoundException;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.animation.AnimationTimer;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Scanner;

public class Test extends Application {
	private Stage s;
	private Pane pane1;
	private Pane gp;
	private ImageView image;
	private VBox vbox;
	private ArrayList<Scene> scenes = new ArrayList<>();
	private ArrayList<javafx.scene.shape.Path> FxPaths = new ArrayList<javafx.scene.shape.Path>();
	private double time;
	private AnimationTimer timer;
	private ArrayList<Rectangle> cars = new ArrayList<>();
	private ArrayList<PathTransition> carTransitions = new ArrayList<>();
	private ArrayList<Circle> lights = new ArrayList<>();
	private int crashes;
	private int score;
	private File nextLevel;
	private Button next;
	private File[] files = new File[] { new File(getClass().getResource("/application/levels/level1.txt").getFile()), new File(getClass().getResource("/application/levels/level2.txt").getFile()),
			new File(getClass().getResource("/application/levels/level3.txt").getFile()), new File(getClass().getResource("/application/levels/level4.txt").getFile()), new File(getClass().getResource("/application/levels/level5.txt").getFile()) };

	public void start(Stage s) throws Exception {

		this.s = s;
		final File[] initialFile = new File[1];
		Image im = new Image(getClass().getResource("/application/assets/image.jpg").toExternalForm());
		this.image = new ImageView(im);
		Button button1 = new Button("START GAME");
		Button button2 = new Button("IMPORT LEVEL");
		button1.setStyle("-fx-background-color:WHITE");
		button2.setStyle("-fx-background-color:WHITE");

		this.vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setPrefSize(600, 500);
		vbox.getChildren().addAll(button1, button2);
		this.pane1 = new Pane();
		pane1.getChildren().addAll(image, vbox);

		Scene scene1 = new Scene(pane1, 600, 500);
		scenes.add(scene1);
		showScene(scene1);

		button1.setOnAction(event -> {
			// Load the next scene if the Start button is clicked
			if (initialFile[0] == null)
				initialFile[0] = files[0];

			Button back = new Button("BACK");
			back.setPrefSize(140, 50);

			Button start = new Button("START");
			start.setPrefSize(140, 50);

			VBox box = new VBox();
			box.setSpacing(20);
			box.setPrefSize(600, 500);
			box.getChildren().addAll(back, start);
			box.setAlignment(Pos.CENTER);
			Pane pane2 = new Pane();
			pane2.getChildren().addAll(image, box);
			Scene scene2 = new Scene(pane2, 600, 500);
			showScene(scene2);

			back.setOnAction(e -> {
				pane1.getChildren().clear();
				pane1.getChildren().addAll(image, vbox);
				showScene(scene1);
			});
			// Loads the game if the Start button is clicked
			start.setOnAction(e -> {
				try {

					FxPaths.clear();
					lights.clear();
					carTransitions.clear();
					showScene(startGame(initialFile[0]));
					this.next = new Button("NEXT LEVEL");

					next.setOnAction(ev -> {
						timer.stop();
						FxPaths.clear();
						lights.clear();
						carTransitions.clear();

						initialFile[0] = nextLevel;
						try {
							showScene(startGame(initialFile[0]));
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					});

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			});

		});
		button2.setOnAction(event -> {
			// Loads the next scene if "Import Level" is clicked.

			Button button01 = new Button("LEVEL 1");
			Button button02 = new Button("LEVEL 2");
			Button button03 = new Button("LEVEL 3");
			Button button04 = new Button("LEVEL 4");
			Button button05 = new Button("LEVEL 5");

			VBox box = new VBox();
			box.setSpacing(20);
			box.setAlignment(Pos.CENTER);
			box.setPrefSize(600, 500);
			box.getChildren().addAll(button01, button02, button03, button04, button05);
			pane1.getChildren().clear();
			pane1.getChildren().addAll(image, box);
			showScene(scene1);

			button01.setOnAction(e -> {
				initialFile[0] = files[0];
				pane1.getChildren().clear();
				pane1.getChildren().addAll(image, vbox);
				showScene(scene1);
			});

			button02.setOnAction(e -> {
				initialFile[0] = files[1];
				pane1.getChildren().clear();
				pane1.getChildren().addAll(image, vbox);
				showScene(scene1);
			});

			button03.setOnAction(e -> {
				initialFile[0] = files[2];
				pane1.getChildren().clear();
				pane1.getChildren().addAll(image, vbox);
				showScene(scene1);
			});

			button04.setOnAction(e -> {
				initialFile[0] = files[3];
				pane1.getChildren().clear();
				pane1.getChildren().addAll(image, vbox);
				showScene(scene1);
			});

			button05.setOnAction(e -> {
				initialFile[0] = files[4];
				pane1.getChildren().clear();
				pane1.getChildren().addAll(image, vbox);
				showScene(scene1);
			});

			button2.setOnAction(e -> {
				pane1.getChildren().clear();
				pane1.getChildren().addAll(image, box);
				showScene(scene1);
			});
		});

	}

	private void showScene(Scene scene) {
		s.setScene(scene);
		s.show();

	}

	private Scene startGame(File file) throws FileNotFoundException, InterruptedException {
		for (int i = 0; i < files.length - 1; i++) {
			if (file.equals(files[i])) {
				this.nextLevel = files[i + 1];
			}
		}

		Scanner in = new Scanner(file);

		ArrayList<Object> a = new ArrayList<>();

		while (in.hasNext()) {
			String word = in.next();
			// This part takes input from the file to create the game objects needed.

			if (word.equals("Metadata")) {
				double m1 = Double.valueOf(in.next());
				double m2 = Double.valueOf(in.next());
				int m3 = Integer.valueOf(in.next());
				int m4 = Integer.valueOf(in.next());
				int m5 = Integer.valueOf(in.next());
				int m6 = Integer.valueOf(in.next());
				int m7 = Integer.valueOf(in.next());

				Metadata m = new Metadata(m1, m2, m3, m4, m5, m6, m7);
				a.add(m);
			} else if (word.equals("RoadTile")) {
				int r1 = Integer.valueOf(in.next());
				int r2 = Integer.valueOf(in.next());
				int r3 = Integer.valueOf(in.next());
				int r4 = Integer.valueOf(in.next());

				RoadTile r = new RoadTile(r1, r2, r3, r4);
				a.add(r);
			} else if (word.equals("Building")) {
				int b1 = Integer.valueOf(in.next());
				int b2 = Integer.valueOf(in.next());
				int b3 = Integer.valueOf(in.next());
				int b4 = Integer.valueOf(in.next());
				int b5 = Integer.valueOf(in.next());

				Building b = new Building(b1, b2, b3, b4, b5);
				a.add(b);
			} else if (word.equals("TrafficLight")) {
				double t1 = Double.valueOf(in.next());
				double t2 = Double.valueOf(in.next());
				double t3 = Double.valueOf(in.next());
				double t4 = Double.valueOf(in.next());

				TrafficLight t = new TrafficLight(t1, t2, t3, t4);
				a.add(t);
			} else if (word.equals("Path")) {
				int p1 = in.nextInt();
				String p2 = in.next();
				double p3 = Double.valueOf(in.next());
				double p4 = Double.valueOf(in.next());

				Path p = new Path(p1, p2, p3, p4);
				a.add(p);
			}
		}
		in.close();

		Pane pane2 = new Pane();
		this.gp = new Pane();
		double X = 0;
		double Y = 0;

		for (Object ob : a) {
			if (ob instanceof Metadata) {

				int cellsOfX = ((Metadata) ob).getGridCellsX();
				int cellsOfY = ((Metadata) ob).getGridCellsY();
				double width = ((Metadata) ob).getWidth();
				double height = ((Metadata) ob).getHeight();

				X = width;
				Y = height;
				gp.setPrefWidth(X);
				gp.setPrefHeight(Y);
				for (int row = 0; row < cellsOfY; row++) {
					for (int col = 0; col < cellsOfX; col++) {
						Rectangle r = new Rectangle(col * ((width / cellsOfX)), row * (height / cellsOfY),
								(width / cellsOfX), (height / cellsOfY));
						r.setStroke(Color.GRAY);
						r.setStrokeWidth(0.1);
						r.setFill(Color.CADETBLUE);
						gp.getChildren().add(r);
					}
				}

				for (Object ob1 : a) {
					if (ob1 instanceof Building) {
						// Create buildings according to the input from the file

						int type = ((Building) ob1).getType();
						int degrees = ((Building) ob1).getDegrees();
						int colorIndex = ((Building) ob1).getColorIndex();
						int cellX = ((Building) ob1).getCellX();
						int cellY = ((Building) ob1).getCellY();

						Color[] colors = new Color[] { Color.ORANGE, Color.PURPLE, Color.BLUE, Color.PINK };

						if (type == 0) {
							Rectangle rOut = new Rectangle(0, 0, (width / cellsOfX) * 2, (height / cellsOfY) * 3);
							Rectangle rIn = new Rectangle(9, 9, ((width / cellsOfX) * 2) - 18,
									((height / cellsOfY) * 2) - 18);
							Rectangle rIn2 = new Rectangle(17, 17, ((width / cellsOfX) * 2) - 34,
									((height / cellsOfY) * 2) - 34);

							rOut.setFill(Color.ALICEBLUE);
							rOut.setStroke(Color.BLACK);
							rOut.setStrokeWidth(3);
							rIn.setFill(colors[colorIndex]);
							rIn2.setFill(colors[colorIndex]);

							rIn.setStrokeWidth(3);
							rIn2.setStrokeWidth(3);
							rIn.setStroke(Color.BLACK);
							rIn2.setStroke(Color.BLACK);

							rIn.setArcWidth(25);
							rIn.setArcHeight(25);
							rIn2.setArcWidth(25);
							rIn2.setArcHeight(25);
							rOut.setArcWidth(25);
							rOut.setArcHeight(25);

							Pane p = new Pane(rOut, rIn, rIn2);
							if (degrees == 0 || degrees == 180) {
								p.setRotate(degrees);
								p.setTranslateX((cellX) * (width / cellsOfX));
								p.setTranslateY((cellY) * (height / cellsOfY));
							}
							if (degrees == 90 || degrees == 270) {
								p.setRotate(-degrees);
								p.setTranslateX((cellX + 0.5) * (width / cellsOfX));
								p.setTranslateY((cellY - 0.5) * (height / cellsOfY));
							}

							gp.getChildren().add(p);

						}
						if (type == 1) {

							Circle circle = new Circle((width / cellsOfX), (height / cellsOfY), width / cellsOfX - 8);
							Circle c = new Circle((width / cellsOfX), (height / cellsOfY), width / cellsOfX - 20);
							Rectangle rOut = new Rectangle(0, 0, (width / cellsOfX) * 2, (height / cellsOfY) * 3);

							circle.setFill(colors[colorIndex]);
							circle.setStrokeWidth(3);
							circle.setStroke(Color.BLACK);

							c.setFill(colors[colorIndex]);
							c.setStrokeWidth(3);
							c.setStroke(Color.BLACK);

							rOut.setFill(Color.ANTIQUEWHITE);
							rOut.setStrokeWidth(3);
							rOut.setStroke(Color.BLACK);
							rOut.setArcWidth(25);
							rOut.setArcHeight(25);

							Pane p = new Pane(rOut, circle, c);

							if (degrees == 0 || degrees == 180) {
								p.setRotate(degrees);
								p.setTranslateX((cellX) * (width / cellsOfX));
								p.setTranslateY((cellY) * (height / cellsOfY));
							}
							if (degrees == 90 || degrees == 270) {
								p.setRotate(-degrees);
								p.setTranslateX((cellX + 0.5) * (width / cellsOfX));
								p.setTranslateY((cellY - 0.5) * (height / cellsOfY));
							}
							gp.getChildren().add(p);

						}
						if (type == 2) {

							Rectangle r = new Rectangle((width / cellsOfX) * cellX, (height / cellsOfY) * cellY,
									(width / cellsOfX), (height / cellsOfY));
							r.setFill(colors[((Building) ob1).getColorIndex()]);
							r.setArcWidth(15);
							r.setArcHeight(25);

							Pane p = new Pane();
							p.getChildren().add(r);
							gp.getChildren().add(p);

						}

					}
				}

				for (Object ob2 : a) {
					if (ob2 instanceof RoadTile) {
						// Create road tiles according to the input from the file

						int type = ((RoadTile) ob2).getType();
						int degrees = ((RoadTile) ob2).getDegrees();
						int cellX = ((RoadTile) ob2).getCellX();
						int cellY = ((RoadTile) ob2).getCellY();

						if (type == 0) {
							Rectangle r = new Rectangle((width / cellsOfX) * cellX, (height / cellsOfY) * cellY + 5,
									(width / cellsOfX), (height / cellsOfY) - 10);
							r.setFill(Color.WHITE);
							r.setRotate(degrees);
							Pane p = new Pane();
							p.getChildren().add(r);
							gp.getChildren().add(p);

						}
						if (type == 1) {
							Arc arc = new Arc(0.1, (height / cellsOfY), (width / cellsOfX) - 5 + 0.1,
									(height / cellsOfY) - 5, 0, 90);
							arc.setFill(Color.WHITE);
							arc.setStroke(Color.TRANSPARENT);
							arc.setType(ArcType.ROUND);

							Arc arcIn = new Arc(0.1, (height / cellsOfY) + 0.1, 5, 5, 0, 90);
							arcIn.setFill(Color.CADETBLUE);
							arcIn.setStroke(Color.TRANSPARENT);
							arcIn.setType(ArcType.ROUND);

							Pane p = new Pane();
							p.setPrefWidth((width / cellsOfX));
							p.setPrefHeight((height / cellsOfY));
							p.getChildren().addAll(arc, arcIn);
							p.setRotate(-degrees);
							p.setTranslateX((width / cellsOfX) * cellX);
							p.setTranslateY((height / cellsOfY) * cellY);
							gp.getChildren().add(p);

						}
						if (type == 2) {

							Rectangle r = new Rectangle(cellX * (width / cellsOfX), cellY * (height / cellsOfY) + 5,
									(width / cellsOfX), (height / cellsOfY) - 10);
							r.setFill(Color.WHITE);

							Rectangle r1 = new Rectangle(cellX * (width / cellsOfX) + 5, cellY * (height / cellsOfY),
									(width / cellsOfX) - 10, (height / cellsOfY));
							r1.setFill(Color.WHITE);

							Pane p = new Pane();
							p.getChildren().addAll(r, r1);
							gp.getChildren().add(p);

						}

						if (type == 3) {
							Rectangle r = new Rectangle(0, 5, (width / cellsOfX), (height / cellsOfY) - 10);
							r.setFill(Color.WHITE);

							Rectangle r1 = new Rectangle(5, 10, (width / cellsOfX) - 10, (height / cellsOfY) - 10);
							r1.setFill(Color.WHITE);

							Pane p = new Pane();
							p.getChildren().addAll(r, r1);
							p.setRotate(degrees);
							p.setTranslateX(cellX * (width / cellsOfX));
							p.setTranslateY(cellY * (height / cellsOfY));
							gp.getChildren().add(p);
						}

					}

				}

				for (Object ob3 : a) {
					if (ob3 instanceof TrafficLight) {
						double x1 = ((TrafficLight) ob3).getX1();
						double y1 = ((TrafficLight) ob3).getY1();
						double x2 = ((TrafficLight) ob3).getX2();
						double y2 = ((TrafficLight) ob3).getY2();

						if (x1 > x2) {
							double temp = x2;
							x2 = x1;
							x1 = temp;
						}

						if (y1 > y2) {
							double temp = y2;
							y2 = y1;
							y1 = temp;
						}

						Line line = new Line(x1, y1, x2, y2);
						line.setStroke(Color.BLACK);

						Circle light = new Circle((x1 + x2) / 2, (y1 + y2) / 2, 5);
						light.setFill(Color.FORESTGREEN);
						lights.add(light);
						light.setOnMouseClicked(event -> {
							if (light.getFill() == Color.FORESTGREEN) {
								light.setFill(Color.RED);

							} else {
								light.setFill(Color.FORESTGREEN);

							}
						});

						gp.getChildren().add(line);
						gp.getChildren().add(light);

					}
				}

				for (Object ob4 : a) {
					if (ob4 instanceof Path) {
						// Create paths according to the coordinates from the file and add them to the
						// FxPaths arraylist

						int index = ((Path) ob4).getIndexOfPath();
						String element = ((Path) ob4).getElement();
						double x = ((Path) ob4).getX();
						double y = ((Path) ob4).getY();

						if (FxPaths.size() <= index) {
							FxPaths.add(new javafx.scene.shape.Path());

						}
						if (element.equals("MoveTo")) {
							MoveTo m = new MoveTo(x, y);
							FxPaths.get(index).getElements().add(m);
						} else if (element.equals("LineTo")) {
							LineTo l = new LineTo(x, y);
							FxPaths.get(index).getElements().add(l);

						}
					}

				}

			} // metadata

		} // object a

		score = 0;
		crashes = 0;

		Label score = new Label("Score:0/100");
		score.setTranslateX(5);
		score.setFont(new Font("Arial", 20));

		Label crashes = new Label("Crashes:0/10");
		crashes.setTranslateX(5);
		crashes.setTranslateY(15);
		crashes.setFont(new Font("Arial", 20));

		Button stop = new Button("STOP");
		stop.setLayoutX(720);
		stop.setLayoutY(760);
		stop.setPrefWidth(70);

		Button back = new Button("BACK");

		Label startingTime = new Label("GO!");
		startingTime.setFont(new Font("Arial", 50));
		startingTime.setTranslateX(400);
		gp.getChildren().add(startingTime);

		

		this.time = 3;
		this.timer = new AnimationTimer() {
			@Override
			public void handle(long now) {

				update(file, gp, score, crashes, stop, back);

			}
		};

		timer.start();

		stop.setOnAction(e -> {
			// Stop the game if the Stop button is clicked
			timer.stop();
			for (PathTransition pT : carTransitions) {
				pT.stop();
				gp.getChildren().remove(pT.getNode());
			}

			VBox popupLayout = new VBox(10);
			popupLayout.setPrefSize(400, 400);
			popupLayout.setAlignment(Pos.CENTER);
			Label messageLabel = new Label("Game is Stopped!");
			Label messageLabel1 = new Label("DOU YOU WANNA CONTINUE?");
			messageLabel.setFont(new Font("Arial", 30));
			Button yesButton = new Button("YES");
			Button noButton = new Button("NO");
			yesButton.setStyle("-fx-background-color: FORESTGREEN;");
			noButton.setStyle("-fx-background-color: RED;");

			Pane p = new Pane();
			p.setStyle("-fx-background-color: lightgray; -fx-background-radius: 10;");
			p.setTranslateX(200);
			p.setTranslateY(200);
			p.setPrefSize(400, 400);
			p.getChildren().add(popupLayout);
			gp.getChildren().add(p);

			yesButton.setPrefSize(100, 50);
			noButton.setPrefSize(100, 50);
			popupLayout.getChildren().addAll(messageLabel, messageLabel1, yesButton, noButton);

			yesButton.setOnAction(event -> {
				// Continue playing the current level if "Yes" is clicked

				gp.getChildren().remove(p);
				timer.start();
			});
			noButton.setOnAction(event -> {
				// Go back to the main menu if "No" is clicked
				pane1.getChildren().clear();
				pane1.getChildren().addAll(image, vbox);
				showScene(scenes.get(0));
			});

		});
		back.setOnAction(event -> {
			// Go back to the first menu if back is clicked.
			timer.stop();
			pane1.getChildren().clear();
			pane1.getChildren().addAll(image, vbox);
			showScene(scenes.get(0));
			this.score = 0;
			this.crashes = 0;

		});

		pane2.getChildren().addAll(gp, stop, score, crashes);
		Scene scene = new Scene(pane2, X, Y);
		return scene;

	}

	private void update(File file, Pane gp, Label score, Label crashes, Button stop, Button back) {

		checkLights();
		checkCrashes(crashes);
		time += 0.026;

		if (time > 4) {
			if (Math.random() < 0.6) {
				if (this.score >= 100) {
					// If the game is won, remove the cars and show a button for the next level
					for (PathTransition pT : carTransitions) {
						pT.stop();
						gp.getChildren().remove(pT.getNode());
					}

					VBox popupLayout = new VBox(10);
					popupLayout.setPrefSize(400, 400);
					popupLayout.setAlignment(Pos.CENTER);
					Label messageLabel = new Label("WIN!!!");
					messageLabel.setFont(new Font("Arial", 30));
					Label messageLabel1 = new Label("YOUR CRASHES:" + this.crashes);
					messageLabel1.setFont(new Font("Arial", 22));
					this.next.setPrefSize(140, 50);
					back.setPrefSize(140, 50);
					this.next.setStyle("-fx-background-color: FORESTGREEN;");
					back.setStyle("-fx-background-color: RED;");

					popupLayout.getChildren().addAll(messageLabel, messageLabel1, back);
					if (file != files[4]) {
						popupLayout.getChildren().add(next);
					}

					Pane p = new Pane();
					p.setStyle("-fx-background-color: lightgray; -fx-background-radius: 10;");
					p.setTranslateX(200);
					p.setTranslateY(200);
					p.setPrefSize(400, 400);
					p.getChildren().add(popupLayout);
					gp.getChildren().add(p);
				}
				if (this.score < 100 && this.crashes >= 10) {
					// If the game is lost, remove the cars and create the button to go back
					for (PathTransition pT : carTransitions) {
						pT.stop();
						gp.getChildren().remove(pT.getNode());
					}

					VBox popupLayout = new VBox(10);
					popupLayout.setPrefSize(400, 400);
					popupLayout.setAlignment(Pos.CENTER);
					Label messageLabel = new Label("GAME OVER!!!");
					messageLabel.setFont(new Font("Arial", 30));
					Label messageLabel1 = new Label("Your Score:" + this.score);
					messageLabel1.setFont(new Font("Arial", 22));

					back.setPrefSize(100, 50);

					popupLayout.getChildren().addAll(messageLabel, messageLabel1, back);

					Pane p = new Pane();
					p.setStyle("-fx-background-color: lightgray; -fx-background-radius: 10;");
					p.setTranslateX(200);
					p.setTranslateY(200);
					p.setPrefSize(400, 400);
					p.getChildren().add(popupLayout);
					gp.getChildren().add(p);

				} else if (this.score < 100 && this.crashes < 10) {
					// If the game is not won or lost, continue spawning new cars
					spawnCar(gp, score, crashes, stop, back);
				}

			}
			time = 0.5;

		}

	}

	private void spawnCar(Pane gp, Label score, Label crashes, Button stop, Button back) {
		// Spawn new cars in random colors, determine paths for them, add them to the
		// game and move them through the paths

		Color[] carColor = new Color[] { Color.RED, Color.GRAY, Color.BLACK, Color.DARKBLUE, Color.BROWN, Color.GREEN };
		Rectangle r = new Rectangle(18, 10);
		r.setFill(carColor[(int) (Math.random() * carColor.length)]);
		r.setArcHeight(5);
		r.setArcWidth(9);
		cars.add(r);
		gp.getChildren().add(r);

		javafx.scene.shape.Path randomPath = FxPaths.get((int) (Math.random() * FxPaths.size()));
		PathTransition pTran = new PathTransition();
		pTran.setNode(r);
		pTran.setPath(randomPath);
		pTran.setCycleCount(1);
		pTran.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pTran.setRate(0.04);
		carTransitions.add(pTran);
		pTran.setOnFinished(e -> {
			this.score++;
			carTransitions.remove(pTran);
			score.setText("Score:" + this.score + "/100");
			gp.getChildren().remove(r);
			cars.remove(r);
		});
		pTran.play();

	}

	public void checkCrashes(Label crashes) {
		// Check for any car crash and add them to the "crashes" variable, also remove
		// them from the game
		for (int i = 0; i < carTransitions.size(); i++) {
			for (int j = i + 1; j < carTransitions.size(); j++) {
				PathTransition car1Transition = carTransitions.get(i);
				PathTransition car2Transition = carTransitions.get(j);

				Bounds car1Bounds = car1Transition.getNode().localToScene(car1Transition.getNode().getBoundsInLocal());
				Bounds car2Bounds = car2Transition.getNode().localToScene(car2Transition.getNode().getBoundsInLocal());

				double distanceX = Math.abs(car1Bounds.getMinX() - car2Bounds.getMinX());
				double distanceY = Math.abs(car1Bounds.getMinY() - car2Bounds.getMinY());
				double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
				double ThresholdDistance = 10;
				if (distance <= ThresholdDistance && !(car1Transition.getPath().equals(car2Transition.getPath()))
						&& !(car1Transition.getStatus() == Animation.Status.PAUSED)) {
					this.crashes++;
					crashes.setText("Crashes: " + this.crashes + "/10");
					car1Transition.stop();
					car2Transition.stop();

					Platform.runLater(() -> {
						PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));
						pauseTransition.setOnFinished(event -> {
							gp.getChildren().removeAll(car1Transition.getNode(), car2Transition.getNode());
						});
						pauseTransition.play();
					});

					carTransitions.remove(car1Transition);
					carTransitions.remove(car2Transition);
					i--;
					break;
				}
			}
		}
	}

	public void checkLights() {

		// This function checks the distances between the lights and the cars and pauses
		// them if the light they are close to is red, then resumes them if the light
		// turns green.
		for (int i = 0; i < carTransitions.size(); i++) {
			for (Circle light : lights) {
				Bounds carBounds = carTransitions.get(i).getNode()
						.localToScene(carTransitions.get(i).getNode().getBoundsInLocal());
				Bounds lightBounds = light.localToScene(light.getBoundsInLocal());

				double distanceX = Math.abs(carBounds.getMinX() - lightBounds.getMinX());
				double distanceY = Math.abs(carBounds.getMinY() - lightBounds.getMinY());
				double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

				double ThresholdDistance = 12;
				if (distance <= ThresholdDistance && light.getFill().equals(Color.RED)) {
					carTransitions.get(i).pause();

				}
				if (distance <= ThresholdDistance && light.getFill().equals(Color.FORESTGREEN)) {
					carTransitions.get(i).play();

				}
			}
		}
		// Also pause the cars coming from behind
		for (int i = 0; i < carTransitions.size(); i++) {
			for (int j = i + 1; j < carTransitions.size(); j++) {
				PathTransition car1Transition = carTransitions.get(i);
				PathTransition car2Transition = carTransitions.get(j);

				Bounds car1Bounds = car1Transition.getNode().localToScene(car1Transition.getNode().getBoundsInLocal());
				Bounds car2Bounds = car2Transition.getNode().localToScene(car2Transition.getNode().getBoundsInLocal());

				double distanceX = Math.abs(car1Bounds.getMinX() - car2Bounds.getMinX());
				double distanceY = Math.abs(car1Bounds.getMinY() - car2Bounds.getMinY());
				double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

				double ThresholdDistance = 20;

				if (distance <= ThresholdDistance && car1Transition.getStatus() == Animation.Status.PAUSED) {
					car2Transition.pause();

				}
				if (distance <= ThresholdDistance && car1Transition.getStatus() == Animation.Status.RUNNING) {
					car2Transition.play();

				}

			}
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

}