
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class MainTest {

    @AfterEach
    public void tearDown() {}

    @Test
    public void test1() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        PrintStream stdout = System.out;

        //before
        System.setOut(ps);

        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("-1\n".getBytes()));

        Main.main(null);

        //after
        System.setIn(stdin);
        System.setOut(stdout);

        String outputText = byteArrayOutputStream.toString();
        System.out.println(outputText);
        assertEquals("Enter the number of sunbeds: ", outputText);
    }


}

