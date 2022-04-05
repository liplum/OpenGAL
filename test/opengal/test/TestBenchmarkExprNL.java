package opengal.test;

import opengal.extension.Memory;
import opengal.extension.Timing;
import opengal.nl.SerializeUtils;
import opengal.syntax.Expression;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.*;

@ExtendWith({Timing.class, Memory.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestBenchmarkExprNL {
    public static int benchmarkNumber = 1000;
    @Test
    @Order(0)
    @Tag("slow")
    public void benchmarkExprSerialize() throws IOException {
        for (int i = 0; i < benchmarkNumber; i++) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            SerializeUtils.serializeExpr(dos, TestExprNL.original);
            dos.flush();
            TestExprNL.bytes = os.toByteArray();
        }
    }

    @Test
    @Order(1)
    @Tag("slow")
    public void benchmarkExprDeserialize() throws IOException {
        for (int i = 0; i < benchmarkNumber; i++) {
            ByteArrayInputStream is = new ByteArrayInputStream(TestExprNL.bytes);
            DataInputStream dis = new DataInputStream(is);
            Expression<?> expr = SerializeUtils.deserializeExpr(dis);
        }
    }

}
