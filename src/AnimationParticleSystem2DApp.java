import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import particlesystem2d.controller.AnimationController;
import particlesystem2d.controller.PropertiesEditorController;
import particlesystem2d.model.Emitter;
import particlesystem2d.model.FireEmitter;
import particlesystem2d.model.Particle;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by GFH on 06.09.2015.
 */
public class AnimationParticleSystem2DApp extends Application {
    private Emitter emitter = new FireEmitter();
    private List<Particle> particles = new LinkedList<>();
    private GraphicsContext graphicsContext;


    private AnimationController animationController;
    PropertiesEditorController propertiesEditorController;

    private Parent createWorld() {
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        try {
            root = loader.load(getClass().getResourceAsStream("/particlesystem2d/fxml/Animation.fxml"));
            animationController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Canvas canvas = new Canvas(600, 600);
        graphicsContext = canvas.getGraphicsContext2D();
        animationController.getPane().getChildren().add(canvas);
        return root;
    }

    private Parent createPropertiesEditorWorld() {
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        try {
            root = loader.load(getClass().getResourceAsStream("/particlesystem2d/fxml/PropertiesEditorController.fxml"));
            propertiesEditorController = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createWorld()));
        primaryStage.setTitle("Particle System 2D Application");
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        animationTimer.start();
        primaryStage.show();

        Stage propertiesStage = new Stage();
        propertiesStage.setScene(new Scene(createPropertiesEditorWorld()));
        propertiesStage.setTitle("Properties Editor");
        propertiesEditorController.initPropertyShit(emitter);
        propertiesStage.show();
    }


    private void onUpdate() {
        graphicsContext.setGlobalAlpha(1);
        graphicsContext.setGlobalBlendMode(BlendMode.SRC_OVER);
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, 600, 600);

        particles.addAll(emitter.emit(300, 300));
        for (Iterator<Particle> it = particles.iterator(); it.hasNext(); ) {
            Particle p = it.next();
            p.update();
            if (!p.isAlive()) {
                it.remove();
                continue;
            }
            p.render(graphicsContext);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
