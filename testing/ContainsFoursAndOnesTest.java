import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ContainsFoursAndOnesTest {
    private int[] array;
    private boolean a;
    private ArrayClass arrayClass;

    public ContainsFoursAndOnesTest(boolean a, int[] array) {
        this.a = a;
        this.array = array;
    }

    @Before
    public void init(){
        arrayClass =  new ArrayClass();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {false, new int[]{}},
                {false, new int[]{1, 1, 1, 1}},
                {false, new int[]{4, 4, 4, 4}},
                {false, new int[]{1, 2, 3, 4, 5}},
                {true, new int[]{1, 4, 4, 1}}
        });
    }

    @Test
    public void testFoursAndOnes(){
        Assert.assertEquals(a, arrayClass.containsFoursAndOnes(array));
    }
}
