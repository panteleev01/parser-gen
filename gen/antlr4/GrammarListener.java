// Generated from /Users/zaharpanteleev/hws/mt/lab4/src/main/java/antlr4/Grammar.g4 by ANTLR 4.13.1
package antlr4;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarParser}.
 */
public interface GrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammarParser#main}.
	 * @param ctx the parse tree
	 */
	void enterMain(GrammarParser.MainContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#main}.
	 * @param ctx the parse tree
	 */
	void exitMain(GrammarParser.MainContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#mainRule}.
	 * @param ctx the parse tree
	 */
	void enterMainRule(GrammarParser.MainRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#mainRule}.
	 * @param ctx the parse tree
	 */
	void exitMainRule(GrammarParser.MainRuleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code matchRule}
	 * labeled alternative in {@link GrammarParser#matcher}.
	 * @param ctx the parse tree
	 */
	void enterMatchRule(GrammarParser.MatchRuleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code matchRule}
	 * labeled alternative in {@link GrammarParser#matcher}.
	 * @param ctx the parse tree
	 */
	void exitMatchRule(GrammarParser.MatchRuleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code matchTerminal}
	 * labeled alternative in {@link GrammarParser#matcher}.
	 * @param ctx the parse tree
	 */
	void enterMatchTerminal(GrammarParser.MatchTerminalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code matchTerminal}
	 * labeled alternative in {@link GrammarParser#matcher}.
	 * @param ctx the parse tree
	 */
	void exitMatchTerminal(GrammarParser.MatchTerminalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#rule}.
	 * @param ctx the parse tree
	 */
	void enterRule(GrammarParser.RuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#rule}.
	 * @param ctx the parse tree
	 */
	void exitRule(GrammarParser.RuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(GrammarParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(GrammarParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#namedRule}.
	 * @param ctx the parse tree
	 */
	void enterNamedRule(GrammarParser.NamedRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#namedRule}.
	 * @param ctx the parse tree
	 */
	void exitNamedRule(GrammarParser.NamedRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#rightSide}.
	 * @param ctx the parse tree
	 */
	void enterRightSide(GrammarParser.RightSideContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#rightSide}.
	 * @param ctx the parse tree
	 */
	void exitRightSide(GrammarParser.RightSideContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#callArgs}.
	 * @param ctx the parse tree
	 */
	void enterCallArgs(GrammarParser.CallArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#callArgs}.
	 * @param ctx the parse tree
	 */
	void exitCallArgs(GrammarParser.CallArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#terminal}.
	 * @param ctx the parse tree
	 */
	void enterTerminal(GrammarParser.TerminalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#terminal}.
	 * @param ctx the parse tree
	 */
	void exitTerminal(GrammarParser.TerminalContext ctx);
}