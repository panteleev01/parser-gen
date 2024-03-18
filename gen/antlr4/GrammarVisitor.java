// Generated from /Users/zaharpanteleev/hws/mt/lab4/src/main/java/antlr4/Grammar.g4 by ANTLR 4.13.1
package antlr4;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GrammarParser#main}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMain(GrammarParser.MainContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#mainRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainRule(GrammarParser.MainRuleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code matchRule}
	 * labeled alternative in {@link GrammarParser#matcher}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMatchRule(GrammarParser.MatchRuleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code matchTerminal}
	 * labeled alternative in {@link GrammarParser#matcher}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMatchTerminal(GrammarParser.MatchTerminalContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#rule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRule(GrammarParser.RuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(GrammarParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#namedRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedRule(GrammarParser.NamedRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#rightSide}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRightSide(GrammarParser.RightSideContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#callArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallArgs(GrammarParser.CallArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#terminal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal(GrammarParser.TerminalContext ctx);
}