package Controller;

import java.nio.charset.Charset;
import org.springframework.http.MediaType;

/**
 * Created by p1317074 on 17/11/16.
 */
public class TestControllerUtils {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );
}
