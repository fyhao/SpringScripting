var BufferedReader = java.io.BufferedReader;
var FileReader = java.io.FileReader;
var File = java.io.File;
var PrintWriter = java.io.PrintWriter;
var BufferedWriter = java.io.BufferedWriter;
var FileWriter = java.io.FileWriter;
var filename = "test.txt";
var str = Math.random() + "d";
var pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(filename))));
pw.println(str);
pw.close();
var br = new BufferedReader(new FileReader(new File(filename)));
var res = br.readLine();
br.close();
var a = str + "|" + res;
var f = new File(filename);
f.delete();
a;