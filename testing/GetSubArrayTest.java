import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetSubArrayTest {
    private ArrayClass arrayClass;

    @Before
    public void init(){
        arrayClass =  new ArrayClass();
    }


    @Test(expected = RuntimeException.class)
    public void testGetSubArray1(){
        Assert.assertArrayEquals(new int[]{}, arrayClass.getSubArray(new int[]{}));
    }

    @Test(expected = RuntimeException.class)
    public void testGetSubArray2(){
        Assert.assertArrayEquals(new int[]{}, arrayClass.getSubArray(new int[]{1, 2, 3}));
    }

    @Test
    public void testGetSubArray3(){
        Assert.assertArrayEquals(new int[]{5, 6}, arrayClass.getSubArray(new int[]{1, 2, 3, 4, 5, 6}));
    }

    @Test
    public void testGetSubArray4(){
        Assert.assertArrayEquals(new int[]{}, arrayClass.getSubArray(new int[]{4, 4, 4, 4, 1, 4}));
    }
}
