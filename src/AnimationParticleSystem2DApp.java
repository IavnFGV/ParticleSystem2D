import com.sun.javafx.perf.PerformanceTracker;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Created by GFH on 06.09.2015.
 */
public class AnimationParticleSystem2DApp extends Application {
    private Emitter emitter = new FireEmitter();
    private List<Particle> particles = new ArrayList<>();
    private GraphicsContext graphicsContext;
    private PerformanceTracker tracker;
    private AnimationController animationController;
    private PropertiesEditorController propertiesEditorController;

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

    private Stage propertiesStage = new Stage();
    ;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createWorld()));
        primaryStage.setTitle("Particle System 2D Application");
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
                propertiesStage.setTitle("FPS = " + tracker.getAverageFPS());
                tracker.resetAverageFPS();
            }
        };

        primaryStage.show();


        propertiesStage.setScene(new Scene(createPropertiesEditorWorld()));
        propertiesStage.setTitle("Properties Editor");
        propertiesEditorController.initPropertyShit(emitter);
        propertiesStage.show();
        tracker = PerformanceTracker.getSceneTracker(primaryStage.getScene());
        animationTimer.start();


//        service.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//                if (getTank().isEngineOn()) {
//                    SoundManager.playSound("moving");
//                }
//            }
//        }, 0, 154, TimeUnit.MILLISECONDS);


    }

    private int flag;

    private void render() {
        switch (flag % 3) {
            case 0: {
                IntStream.range(0, particles.size())
                        .filter(i -> i % 3 == 0)
                        .forEach(i -> particles.get(i).render(graphicsContext));
            }
            case 1: {
                IntStream.range(0, particles.size())
                        .filter(i -> i % 3 == 1)
                        .forEach(i -> particles.get(i).render(graphicsContext));
            }
            case 2: {
                IntStream.range(0, particles.size())
                        .filter(i -> i % 3 == 2)
                        .forEach(i -> particles.get(i).render(graphicsContext));
            }
        }
        flag = ++flag % 3;

    }

    private Predicate<Integer> evenNumber = n -> n % 2 == 0;

    // initial set partitioned by the predicate
//    Map<Boolean, List<Integer>> partitioned = set.stream().collect(Collectors.partitioningBy(evenNumber));


    private void onUpdate() {
        graphicsContext.setGlobalAlpha(1);
        graphicsContext.setGlobalBlendMode(BlendMode.SRC_OVER);
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, 600, 600);

        particles.addAll(emitter.emit(300, 300));
//        for (int i = 0; i < particles.size(); i++) {
//            Particle p =  particles.get(i);
//            p.update();
//            if (!p.isAlive()){
//                particles.remove(i);
//                continue;
//            }
//            p.render(graphicsContext);
//
//        }
        for (Iterator<Particle> it = particles.iterator(); it.hasNext(); ) {
            Particle p = it.next();
            p.update();
            if (!p.isAlive()) {
                it.remove();
                continue;
            }
            // p.render(graphicsContext);
        }
        render();
    }

    public static void main(String[] args) {
        launch(args);
    }

    ThreadFactory threadFactory =
            new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread t = Executors.defaultThreadFactory().newThread(r);
                    t.setDaemon(true);
                    return t;
                }
            };

    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(threadFactory);
}
