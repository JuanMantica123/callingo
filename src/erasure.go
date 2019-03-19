package main

import "C"
import (
    "bytes"
    "erasure"
)

var (
    e *erasure.Encoder
)

func init(){
     ep, _ := erasure.ParseEncoderParams(1, 3, erasure.Vandermonde);
     e = erasure.NewEncoder(ep);

}

//export Encode
func Encode(input []byte) (string) {
    var str string;
    res,_ := e.Encode(input);
    for _, element := range res {
        n := bytes.IndexByte(element,0);
        s := string(element[:n]);
        str += s;
        println(str);
    }
    return str;
}

//export Decode
func Decode(encodedString string, length int) (string) {
    var byteMatrix [][]byte;
    for i :=0;i< len(encodedString);i+=length {
        str := encodedString[i : i+length];
        byteMatrix =append(byteMatrix,[]byte(str));
    }
    res,_ := e.Decode(byteMatrix, length);
    //n := bytes.IndexByte(res,0);
    decodedString := string(res);
    return  decodedString;
}
func main(){

}
