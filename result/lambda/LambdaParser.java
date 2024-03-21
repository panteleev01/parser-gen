

import static util.LambdaUtil.*;

import java.text.ParseException;
import java.util.EnumSet;

public class LambdaParser {
    private final LambdaLexicalAnalyzer analyzer;

    public LambdaParser(LambdaLexicalAnalyzer analyzer) throws ParseException {
        this.analyzer = analyzer;
        analyzer.nextToken();
    }


public Node e() throws ParseException {
        switch (curToken().token) {

case VARNAME:
case N:
case MINUS:
case O:
var t0 = t();var eTail1 = eTail(t0);{ return eTail1; }}throw new ParseException("unknown token", analyzer.curPos());}
public Node eTail(Node head) throws ParseException {
        switch (curToken().token) {

case PLUS:
check(curToken().token == LambdaToken.PLUS);
var PLUS0 = curToken().value;
nextToken();
var t1 = t();var eTail2 = eTail(new Node(head,t1,"add"));{ return eTail2; }case MINUS:
check(curToken().token == LambdaToken.MINUS);
var MINUS3 = curToken().value;
nextToken();
var t4 = t();var eTail5 = eTail(new Node(head, t4, "sub"));{ return eTail5; }case C:
case END:
{ return head; }}throw new ParseException("unknown token", analyzer.curPos());}
public Node t() throws ParseException {
        switch (curToken().token) {

case VARNAME:
case N:
case MINUS:
case O:
var f0 = f();var tTail1 = tTail(f0);{ return tTail1; }}throw new ParseException("unknown token", analyzer.curPos());}
public Node tTail(Node head) throws ParseException {
        switch (curToken().token) {

case MUL:
check(curToken().token == LambdaToken.MUL);
var MUL0 = curToken().value;
nextToken();
var f1 = f();var tTail2 = tTail(new Node(head, f1, "mul"));{ return tTail2; }case DIV:
check(curToken().token == LambdaToken.DIV);
var DIV3 = curToken().value;
nextToken();
var f4 = f();var tTail5 = tTail(new Node(head, f4, "div"));{ return tTail5; }case C:
case END:
case PLUS:
case MINUS:
{ return head; }}throw new ParseException("unknown token", analyzer.curPos());}
public Node f() throws ParseException {
        switch (curToken().token) {

case N:
check(curToken().token == LambdaToken.N);
var N0 = curToken().value;
nextToken();
{ return new Node(N0); }case MINUS:
check(curToken().token == LambdaToken.MINUS);
var MINUS1 = curToken().value;
nextToken();
check(curToken().token == LambdaToken.N);
var N2 = curToken().value;
nextToken();
{ return new Node("-" + N2); }case O:
check(curToken().token == LambdaToken.O);
var O3 = curToken().value;
nextToken();
var e4 = e();check(curToken().token == LambdaToken.C);
var C5 = curToken().value;
nextToken();
{ return e4; }case VARNAME:
check(curToken().token == LambdaToken.VARNAME);
var VARNAME6 = curToken().value;
nextToken();
{ return new Node(VARNAME6); }}throw new ParseException("unknown token", analyzer.curPos());}
public Lambda main() throws ParseException {
        switch (curToken().token) {

case LAMBDA:
check(curToken().token == LambdaToken.LAMBDA);
var LAMBDA0 = curToken().value;
nextToken();
var names1 = names(new Args());check(curToken().token == LambdaToken.SEMICOLON);
var SEMICOLON2 = curToken().value;
nextToken();
var e3 = e();{ return new Lambda(names1, e3); }}throw new ParseException("unknown token", analyzer.curPos());}
public Args names(Args args) throws ParseException {
        switch (curToken().token) {

case VARNAME:
check(curToken().token == LambdaToken.VARNAME);
var VARNAME0 = curToken().value;
nextToken();
var namestail1 = namestail(args.add(VARNAME0));{ return namestail1; }case SEMICOLON:
{ return args; }}throw new ParseException("unknown token", analyzer.curPos());}
public Args namestail(Args args) throws ParseException {
        switch (curToken().token) {

case COMMA:
check(curToken().token == LambdaToken.COMMA);
var COMMA0 = curToken().value;
nextToken();
check(curToken().token == LambdaToken.VARNAME);
var VARNAME1 = curToken().value;
nextToken();
var namestail2 = namestail(args.add(VARNAME1));{ return namestail2; }case SEMICOLON:
{ return args; }}throw new ParseException("unknown token", analyzer.curPos());}   private LambdaTokenWrapper curToken() {
        return analyzer.curToken;
   }

   private void nextToken() throws ParseException {
       analyzer.nextToken();
   }

   private void check(boolean b) throws ParseException {
       if (!b) {
           throw new ParseException("error", analyzer.curPos());
       }
   }
    public Lambda parse() throws ParseException {
        var res = main();
        if (analyzer.curToken.token != LambdaToken.END)
            throw new ParseException("End of input expected", analyzer.curPos());
        return res;
    }

}