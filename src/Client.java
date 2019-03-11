import com.sun.jna.*;
import java.util.*;

public class Client {
    public interface Erasure extends Library {
        // GoSlice class maps to:
        // C type struct { void *data; GoInt len; GoInt cap; }
        public class GoSlice extends Structure {
            public static class ByValue extends GoSlice implements Structure.ByValue {}
            public Pointer data;
            public long len;
            public long cap;
            protected List getFieldOrder(){
                return Arrays.asList(new String[]{"data","len","cap"});
            }
        }

        // GoString class maps to:
        // C type struct { const char *p; GoInt n; }
        public class GoString extends Structure {
            public static class ByValue extends GoString implements Structure.ByValue {}
            public String p;
            public long n;
            protected List getFieldOrder(){
                return Arrays.asList(new String[]{"p","n"});
            }

        }

         GoSlice Encode(GoSlice.ByValue bytes);
         GoSlice Decode(GoSlice p0, long p1);
    }

    static public void main(String argv[]) {

        //Replace this path with the path on your local machine
        Erasure erasure = Native.load(
                "/home/pedro/Documents/juan_work/callingErasure/src/erasure.so", Erasure.class);
        byte[] bytes = "data".getBytes();

        Memory arr = new Memory(bytes.length * Native.getNativeSize(Byte.TYPE));
        arr.write(0, bytes, 0, bytes.length);

        Erasure.GoSlice.ByValue slice = new Erasure.GoSlice.ByValue();
        slice.data = arr;
        slice.len = bytes.length;
        slice.cap = bytes.length;
        System.out.println(slice.data);
        Erasure.GoSlice encodedSlice = erasure.Encode(slice);
        System.out.println(erasure.Decode(encodedSlice,slice.len));


    }
}