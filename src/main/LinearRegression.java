package main;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LinearRegression extends Application {
	private Canvas can;
	private GraphicsContext gc;
	
	private ArrayList<Point2D> points = new ArrayList<Point2D>();
	
	private double m = 0, b = 0;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() throws Exception {
	}
	
	public void start(Stage stage) throws Exception {
		Pane root = new Pane();
		Scene scene = new Scene(root, 800, 800);

		stage.setTitle("Sample Text");

		can = new Canvas(scene.getWidth(), scene.getHeight());
		gc = can.getGraphicsContext2D();

		root.getChildren().add(can);
		// root.setStyle("-fx-background-color: #000000");

		scene.widthProperty().addListener((obsv, oldVal, newVal) -> {
			can.setWidth(newVal.doubleValue());
		});

		scene.heightProperty().addListener((obsv, oldVal, newVal) -> {
			can.setHeight(newVal.doubleValue());
		});
		
		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				calc(new Point2D(e.getX(), e.getY()));
				draw();
			}
		});

		stage.setScene(scene);
		stage.show();
	}
	
	private void calc(Point2D mouse) {
		double avgX = 0, avgY = 0, sumTop = 0, sumBot = 0;
		points.add(new Point2D(mouse.getX(), mouse.getY()));
		
		for (Point2D p : points) {
			avgX += p.getX();
			avgY += p.getY();
		}
		avgX /= points.size();
		avgY /= points.size();
		
		for (Point2D p : points) {
			sumTop += (p.getX() - avgX) * (p.getY() - avgY);
			sumBot += Math.pow(p.getX() - avgX, 2);
		}
		m = sumTop / sumBot;
		b = avgY - m * avgX;
	}
	
	private void draw() {
		double s = 10;
		gc.clearRect(0, 0, can.getWidth(), can.getHeight());
		for (Point2D p : points) {
			gc.fillOval(p.getX() - s / 2, p.getY() - s / 2, s, s);
		}
		
		if (points.size() > 1) {
			double x1 = 0, y1 = b, x2 = can.getWidth(), y2 = m * x2 + b;
			gc.strokeLine(x1, y1, x2, y2);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}