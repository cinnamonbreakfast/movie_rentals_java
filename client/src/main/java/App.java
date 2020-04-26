import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ui.Console;

public class App {
    public static void main(String[] args)
    {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "config"
                );

        Console console = (Console) context.getBean("appConsole");
        console.runConsole();

    }
}
