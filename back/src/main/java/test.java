import java.util.Arrays;

public class test {


    /**
     * 用于将仓库和库位的信息放入数组中
     *
     * @param result 库位信息文本
     * @param tokens 用于接收的数组
     */
    public static String[] ScanWarehouse(String result, String[] tokens) {
        if (tokens == null) {
            tokens = new String[2];
        }
        tokens[0] = result.substring(0, result.indexOf("#"));
        tokens[1] = result.substring(result.indexOf("#") + 1);
        if (tokens[1] == null || tokens[1] == "") {
            tokens[1] = "##########";
        }
        return tokens;
    }


    public static void main(String[] args) {

        System.out.println(Arrays.toString(ScanWarehouse("101#", new String[2])));

    }
}
