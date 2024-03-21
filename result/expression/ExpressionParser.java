

import static util.ExpressionUtil.*;

import java.text.ParseException;
import java.util.EnumSet;

public class ExpressionParser {
    private final ExpressionLexicalAnalyzer analyzer;

    public ExpressionParser(ExpressionLexicalAnalyzer analyzer) throws ParseException {
        this.analyzer = analyzer;
        analyzer.nextToken();
    }


public Integer e() throws ParseException {
        switch (curToken().token) {

case SO:
case N:
case MINUS:
case O:
var t0 = t();var eTail1 = eTail(t0);{ return eTail1; }}throw new ParseException("unknown token", analyzer.curPos());}
public Integer eTail(Integer head) throws ParseException {
        switch (curToken().token) {

case PLUS:
check(curToken().token == ExpressionToken.PLUS);
var PLUS0 = curToken().value;
nextToken();
var t1 = t();var eTail2 = eTail(head+t1);{ return eTail2; }case MINUS:
check(curToken().token == ExpressionToken.MINUS);
var MINUS3 = curToken().value;
nextToken();
var t4 = t();var eTail5 = eTail(head-t4);{ return eTail5; }case COMMA:
case SC:
case C:
case END:
{ return head; }}throw new ParseException("unknown token", analyzer.curPos());}
public Integer t() throws ParseException {
        switch (curToken().token) {

case SO:
case N:
case MINUS:
case O:
var f0 = f();var tTail1 = tTail(f0);{ return tTail1; }}throw new ParseException("unknown token", analyzer.curPos());}
public Integer tTail(Integer head) throws ParseException {
        switch (curToken().token) {

case MUL:
check(curToken().token == ExpressionToken.MUL);
var MUL0 = curToken().value;
nextToken();
var f1 = f();var tTail2 = tTail(head*f1);{ return tTail2; }case DIV:
check(curToken().token == ExpressionToken.DIV);
var DIV3 = curToken().value;
nextToken();
var f4 = f();var tTail5 = tTail(head/f4);{ return tTail5; }case COMMA:
case SC:
case C:
case END:
case PLUS:
case MINUS:
{ return head; }}throw new ParseException("unknown token", analyzer.curPos());}
public Integer f() throws ParseException {
        switch (curToken().token) {

case N:
check(curToken().token == ExpressionToken.N);
var N0 = curToken().value;
nextToken();
{ return Integer.parseInt(N0); }case MINUS:
check(curToken().token == ExpressionToken.MINUS);
var MINUS1 = curToken().value;
nextToken();
check(curToken().token == ExpressionToken.N);
var N2 = curToken().value;
nextToken();
{ return -Integer.parseInt(N2); }case O:
check(curToken().token == ExpressionToken.O);
var O3 = curToken().value;
nextToken();
var e4 = e();check(curToken().token == ExpressionToken.C);
var C5 = curToken().value;
nextToken();
{ return e4; }case SO:
check(curToken().token == ExpressionToken.SO);
var SO6 = curToken().value;
nextToken();
var e7 = e();check(curToken().token == ExpressionToken.COMMA);
var COMMA8 = curToken().value;
nextToken();
var e9 = e();check(curToken().token == ExpressionToken.SC);
var SC10 = curToken().value;
nextToken();
{ return fact(e7) / fact(e9) / fact(e7-e9); }}throw new ParseException("unknown token", analyzer.curPos());}   private ExpressionTokenWrapper curToken() {
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
    public Integer parse() throws ParseException {
        var res = e();
        if (analyzer.curToken.token != ExpressionToken.END)
            throw new ParseException("End of input expected", analyzer.curPos());
        return res;
    }

}