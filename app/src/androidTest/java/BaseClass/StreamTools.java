package BaseClass;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public  class StreamTools {

    /**
     * 把输入流的数据转化为字符串
     *
     * @param is 输入流
     * @return 字符串
     */
    public static String ReadInputStream(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            is.close();
            baos.close();
            byte[] result = baos.toByteArray();
            return new String(result);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "将输入流转化为字符串失败";
        }
    }
}