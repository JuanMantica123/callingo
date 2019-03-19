import com.sun.jna.*;
import java.util.*;

public class Client {
    public interface Erasure extends Library {
        // GoSlice class maps to:
        // C type struct { void *data; GoInt len; GoInt cap; }
        class GoSlice extends Structure {
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
        class GoString extends Structure {
            public static class ByValue extends GoString implements Structure.ByValue {}
            public String p;
            public long n;
            protected List getFieldOrder(){
                return Arrays.asList(new String[]{"p","n"});
            }

        }

         GoString.ByValue Encode(GoSlice.ByValue bytes);
         GoString.ByValue Decode(GoString.ByValue encodedString, long p1);
    }

    static public void main(String argv[]) {

        //Replace this path with the path on your local machine
        Erasure erasure = Native.load(
                "/home/pedro/Documents/juan_work/callingErasure/src/erasure.so", Erasure.class);
        byte[] bytes = "verylongstringdatajuanherrykishoriinput".getBytes();

        Memory arr = new Memory(bytes.length * Native.getNativeSize(Byte.TYPE));
        arr.write(0, bytes, 0, bytes.length);

        Erasure.GoSlice.ByValue slice = new Erasure.GoSlice.ByValue();
        slice.data = arr;
        slice.len = bytes.length;
        slice.cap = bytes.length;

        Erasure.GoString.ByValue encodedGoString = erasure.Encode(slice);
        System.out.println(encodedGoString.p);

        Erasure.GoString.ByValue decodedGoString = erasure.Decode(encodedGoString,encodedGoString.n/4);
        System.out.println(decodedGoString.p);
    }
}