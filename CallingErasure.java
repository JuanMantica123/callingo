import com.sun.jna.*;

public class CallingErasure {
  public interface Erasure extends Library {
    class GoSlice extends Structure {
      public Pointer data;
      public long len;
      public long cap;

      GoSlice(Pointer data,long len,long cap){
        this.data = data;
        this.len = len;
        this.cap = cap;
      }
    }
    GoSlice Encode(GoSlice p0);

    GoSlice Decode(GoSlice p0, int p1);

  }

  public static void main(String[] args) {
    Erasure erasure = Native.load(
      "/home/pedro/Documents/juan_work/callingErasure/src/calling/erasure/erasure.so", Erasure.class);
    Erasure.GoSlice goSlice = new Erasure.GoSlice(new Pointer(4),1,2);
    Erasure.GoSlice encondedGoSlice = erasure.Encode(goSlice);
    Erasure.GoSlice originalGoSLice = erasure.Decode(encondedGoSlice,5);
  }
}
