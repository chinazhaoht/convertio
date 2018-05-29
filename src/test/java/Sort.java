import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import shop.convertio.model.User;

public class Sort {


    /**
     * 插入排序
     * @param a
     */
    public void insertSort(int[] a){
        int length = a.length;
        int insertNum;   //要插入的数

        for(int i = 1;i<length;i++){
            insertNum = a[i];
            int j = i - 1;
            while(j>0 && insertNum < a[j]){
                a[j+1] = a[j];
                j--;
            }
            a[j+1] = insertNum;
        }
    }


    /**
     * 快速排序
     * @param numbers
     * @param start
     * @param end
     */
    public static void quickSort(int[] numbers, int start, int end) {
        if (start < end) {
            int base = numbers[start]; // 选定的基准值（第一个数值作为基准值）
            int temp; // 记录临时中间值
            int i = start, j = end;
            do {
                while ((numbers[i] < base) && (i < end))
                    i++;
                while ((numbers[j] > base) && (j > start))
                    j--;
                if (i <= j) {
                    temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                    i++;
                    j--;
                }
            } while (i <= j);
            if (start < j)
                quickSort(numbers, start, j);
            if (end > i)
                quickSort(numbers, i, end);
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        User user1 = new User();
        user1.setAge("12");
        user1.setId("hello");
        user1.setName("nihao");
        user1.setSex("girl");



        System.out.println(mapper.writeValueAsString(user1));


    }

}
