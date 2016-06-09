package common;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.FakeApplication;
import play.test.Helpers;

import java.io.IOException;

import static play.test.Helpers.inMemoryDatabase;

/**
 * @author Jean-Baptiste WATENBERG <jbwatenberg@juniorisep.com>
 */
public class BaseModelTest {
    public static Application app;

    public static Application createFakeApp() {
        //return Helpers.fakeApplication(Helpers.inMemoryDatabase());
        Application application = new GuiceApplicationBuilder()
                .configure("db.default.driver", "org.h2.Driver")
                .configure("db.default.url", "jdbc:h2:mem:play")
                .configure("play.http.router", "my.test.Routes")
                .build();
        return application;
    }

    @BeforeClass
    public static void startApp() throws IOException {
        app = createFakeApp();
        Helpers.start(app);
    }

    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }
}
