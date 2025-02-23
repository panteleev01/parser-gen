// Generated from /Users/zaharpanteleev/hws/mt/lab4/src/main/java/antlr4/Grammar.g4 by ANTLR 4.13.1
package antlr4;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class GrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		REGEX=10, BLOCK=11, ID=12, MATCH_NAME=13, NUMBER=14, STRING=15, WHITESPACE=16;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"REGEX", "BLOCK", "ID", "MATCH_NAME", "NUMBER", "STRING", "WHITESPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'main>'", "';'", "':'", "'='", "'|'", "'('", "')'", "','", "'token>++'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, "REGEX", 
			"BLOCK", "ID", "MATCH_NAME", "NUMBER", "STRING", "WHITESPACE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public GrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0010u\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0004\tA\b"+
		"\t\u000b\t\f\tB\u0001\t\u0001\t\u0001\n\u0001\n\u0004\nI\b\n\u000b\n\f"+
		"\nJ\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0005\u000bQ\b\u000b\n\u000b"+
		"\f\u000bT\t\u000b\u0001\f\u0001\f\u0004\fX\b\f\u000b\f\f\fY\u0001\r\u0004"+
		"\r]\b\r\u000b\r\f\r^\u0001\r\u0001\r\u0004\rc\b\r\u000b\r\f\rd\u0003\r"+
		"g\b\r\u0001\u000e\u0001\u000e\u0005\u000ek\b\u000e\n\u000e\f\u000en\t"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0002Jl\u0000\u0010\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004"+
		"\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017"+
		"\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010\u0001\u0000\u0005\u0006"+
		"\u0000  \"\"(;A[]]az\u0002\u0000AZaz\u0003\u000009AZaz\u0001\u000009\u0003"+
		"\u0000\t\n\r\r  |\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001"+
		"\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001"+
		"\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000"+
		"\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000"+
		"\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000"+
		"\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000"+
		"\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000"+
		"\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000"+
		"\u0000\u0001!\u0001\u0000\u0000\u0000\u0003\'\u0001\u0000\u0000\u0000"+
		"\u0005)\u0001\u0000\u0000\u0000\u0007+\u0001\u0000\u0000\u0000\t-\u0001"+
		"\u0000\u0000\u0000\u000b/\u0001\u0000\u0000\u0000\r1\u0001\u0000\u0000"+
		"\u0000\u000f3\u0001\u0000\u0000\u0000\u00115\u0001\u0000\u0000\u0000\u0013"+
		">\u0001\u0000\u0000\u0000\u0015F\u0001\u0000\u0000\u0000\u0017N\u0001"+
		"\u0000\u0000\u0000\u0019U\u0001\u0000\u0000\u0000\u001b\\\u0001\u0000"+
		"\u0000\u0000\u001dh\u0001\u0000\u0000\u0000\u001fq\u0001\u0000\u0000\u0000"+
		"!\"\u0005m\u0000\u0000\"#\u0005a\u0000\u0000#$\u0005i\u0000\u0000$%\u0005"+
		"n\u0000\u0000%&\u0005>\u0000\u0000&\u0002\u0001\u0000\u0000\u0000\'(\u0005"+
		";\u0000\u0000(\u0004\u0001\u0000\u0000\u0000)*\u0005:\u0000\u0000*\u0006"+
		"\u0001\u0000\u0000\u0000+,\u0005=\u0000\u0000,\b\u0001\u0000\u0000\u0000"+
		"-.\u0005|\u0000\u0000.\n\u0001\u0000\u0000\u0000/0\u0005(\u0000\u0000"+
		"0\f\u0001\u0000\u0000\u000012\u0005)\u0000\u00002\u000e\u0001\u0000\u0000"+
		"\u000034\u0005,\u0000\u00004\u0010\u0001\u0000\u0000\u000056\u0005t\u0000"+
		"\u000067\u0005o\u0000\u000078\u0005k\u0000\u000089\u0005e\u0000\u0000"+
		"9:\u0005n\u0000\u0000:;\u0005>\u0000\u0000;<\u0005+\u0000\u0000<=\u0005"+
		"+\u0000\u0000=\u0012\u0001\u0000\u0000\u0000>@\u0005$\u0000\u0000?A\u0007"+
		"\u0000\u0000\u0000@?\u0001\u0000\u0000\u0000AB\u0001\u0000\u0000\u0000"+
		"B@\u0001\u0000\u0000\u0000BC\u0001\u0000\u0000\u0000CD\u0001\u0000\u0000"+
		"\u0000DE\u0005$\u0000\u0000E\u0014\u0001\u0000\u0000\u0000FH\u0005{\u0000"+
		"\u0000GI\t\u0000\u0000\u0000HG\u0001\u0000\u0000\u0000IJ\u0001\u0000\u0000"+
		"\u0000JK\u0001\u0000\u0000\u0000JH\u0001\u0000\u0000\u0000KL\u0001\u0000"+
		"\u0000\u0000LM\u0005}\u0000\u0000M\u0016\u0001\u0000\u0000\u0000NR\u0007"+
		"\u0001\u0000\u0000OQ\u0007\u0002\u0000\u0000PO\u0001\u0000\u0000\u0000"+
		"QT\u0001\u0000\u0000\u0000RP\u0001\u0000\u0000\u0000RS\u0001\u0000\u0000"+
		"\u0000S\u0018\u0001\u0000\u0000\u0000TR\u0001\u0000\u0000\u0000UW\u0005"+
		"#\u0000\u0000VX\u0007\u0001\u0000\u0000WV\u0001\u0000\u0000\u0000XY\u0001"+
		"\u0000\u0000\u0000YW\u0001\u0000\u0000\u0000YZ\u0001\u0000\u0000\u0000"+
		"Z\u001a\u0001\u0000\u0000\u0000[]\u0007\u0003\u0000\u0000\\[\u0001\u0000"+
		"\u0000\u0000]^\u0001\u0000\u0000\u0000^\\\u0001\u0000\u0000\u0000^_\u0001"+
		"\u0000\u0000\u0000_f\u0001\u0000\u0000\u0000`b\u0005.\u0000\u0000ac\u0007"+
		"\u0003\u0000\u0000ba\u0001\u0000\u0000\u0000cd\u0001\u0000\u0000\u0000"+
		"db\u0001\u0000\u0000\u0000de\u0001\u0000\u0000\u0000eg\u0001\u0000\u0000"+
		"\u0000f`\u0001\u0000\u0000\u0000fg\u0001\u0000\u0000\u0000g\u001c\u0001"+
		"\u0000\u0000\u0000hl\u0005\"\u0000\u0000ik\t\u0000\u0000\u0000ji\u0001"+
		"\u0000\u0000\u0000kn\u0001\u0000\u0000\u0000lm\u0001\u0000\u0000\u0000"+
		"lj\u0001\u0000\u0000\u0000mo\u0001\u0000\u0000\u0000nl\u0001\u0000\u0000"+
		"\u0000op\u0005\"\u0000\u0000p\u001e\u0001\u0000\u0000\u0000qr\u0007\u0004"+
		"\u0000\u0000rs\u0001\u0000\u0000\u0000st\u0006\u000f\u0000\u0000t \u0001"+
		"\u0000\u0000\u0000\t\u0000BJRY^dfl\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}