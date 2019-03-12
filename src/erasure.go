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
func Decode(input []string, length int, buffer []byte) ([]byte) {
    res,_ := e.Decode(input, length);i
    return res;
    return  nil;
}
func main(){

}
