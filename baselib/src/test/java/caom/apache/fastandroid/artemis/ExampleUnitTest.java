package caom.apache.fastandroid.artemis;

import com.apache.fastandroid.artemis.TypeInfoManager;
import com.apache.fastandroid.artemis.support.bean.TypeInfoBean;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testTypeInfo() throws Exception {
        TypeInfoManager manager = new TypeInfoManager();
        TypeInfoBean bean = manager.getTypeBean(TypeInfoManager.GET_ONE | TypeInfoManager.GET_FIVE);
        System.out.println("bean = "+ bean);
    }

}