package starter;

import config.DataConfig;
import config.RootConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ItemApplication {

	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(RootConfig.class, DataConfig.class);
		ctx.start();
        // press any key to exit
        System.in.read();
	}

}
