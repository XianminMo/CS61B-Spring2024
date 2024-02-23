import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }

     @Test
     void testAddFirstAndAddLast() {
         Deque61B<Integer> List = new ArrayDeque61B<>();
         List.addFirst(1); // [1]
         List.addFirst(5); // [5, 1]
         List.addLast(3); // [5, 1, 3]
         List.addLast(9); // [5, 1, 3, 9]
         assertThat(List.toList()).containsExactly(5, 1, 3, 9).inOrder();
     }

     @Test
     void testGet() {
         Deque61B<Integer> List = new ArrayDeque61B<>();
         List.addFirst(1); // [1]
         List.addFirst(5); // [5, 1]
         List.addLast(3); // [5, 1, 3]
         List.addLast(9); // [5, 1, 3, 9]
         assertThat(List.get(2)).isEqualTo(3);
         assertThat(List.get(3)).isEqualTo(9);
         assertThat(List.get(5)).isEqualTo(null);
         assertThat(List.get(-1)).isEqualTo(null);
     }

}
