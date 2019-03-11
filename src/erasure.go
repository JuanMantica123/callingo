package main

import "C"
import (
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
func Encode(input []byte) ([][]byte) {
    println("Encoding data");
    s := string(input[:4]);
    println(s);
    res,_ := e.Encode(input);
    return res;
}

//export Decode
func Decode(input [][]byte, length int) ([]byte) {
    println("Decoding data")
    res,_ := e.Decode(input, length);
    return res;
}
func main(){

}
