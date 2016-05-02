package org.lucene.alalysis;

import java.util.Arrays;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.ElisionFilter;

public class CustomAnalyzer extends Analyzer {

	public static final CharArraySet FRENSH_STOP_WORDS = CharArraySet.unmodifiableSet(
		      new CharArraySet(Arrays.asList(new String[]{
		    		  "a", "afin", "ai", "ainsi", "après", "attendu", "au", "aujourd", "auquel", "aussi",
		    	     "autre", "autres", "aux", "auxquelles", "auxquels", "avait", "avant", "avec", "avoir",
		    	     "c", "car", "ce", "ceci", "cela", "celle", "celles", "celui", "cependant", "certain",
		    	     "certaine", "certaines", "certains", "ces", "cet", "cette", "ceux", "chez", "ci",
		    	     "combien", "comme", "comment", "concernant", "contre", "d", "dans", "de", "debout",
		    	     "dedans", "dehors", "delà", "depuis", "derrière", "des", "désormais", "desquelles",
		    	     "desquels", "dessous", "dessus", "devant", "devers", "devra", "divers", "diverse",
		    	     "diverses", "doit", "donc", "dont", "du", "duquel", "durant", "dès", "elle", "elles",
		    	     "en", "entre", "environ", "est", "et", "etc", "etre", "eu", "eux", "excepté", "hormis",
		    	     "hors", "hélas", "hui", "il", "ils", "j", "je", "jusqu", "jusque", "l", "la", "laquelle",
		    	     "le", "lequel", "les", "lesquelles", "lesquels", "leur", "leurs", "lorsque", "lui", "là",
		    	     "ma", "mais", "malgré", "me", "merci", "mes", "mien", "mienne", "miennes", "miens", "moi",
		    	     "moins", "mon", "moyennant", "même", "mêmes", "n", "ne", "ni", "non", "nos", "notre",
		    	     "nous", "néanmoins", "nôtre", "nôtres", "on", "ont", "ou", "outre", "où", "par", "parmi",
		    	     "partant", "pas", "passé", "pendant", "plein", "plus", "plusieurs", "pour", "pourquoi",
		    	     "proche", "près", "puisque", "qu", "quand", "que", "quel", "quelle", "quelles", "quels",
		    	     "qui", "quoi", "quoique", "revoici", "revoilà", "s", "sa", "sans", "sauf", "se", "selon",
		    	     "seront", "ses", "si", "sien", "sienne", "siennes", "siens", "sinon", "soi", "soit",
		    	     "son", "sont", "sous", "suivant", "sur", "ta", "te", "tes", "tien", "tienne", "tiennes",
		    	     "tiens", "toi", "ton", "tous", "tout", "toute", "toutes", "tu", "un", "une", "va", "vers",
		    	     "voici", "voilà", "vos", "votre", "vous", "vu", "vôtre", "vôtres", "y", "à", "ça", "ès",
		    	     "été", "être", "ô"}), true));;
	
	public static final CharArraySet DEFAULT_ARTICLES = CharArraySet.unmodifiableSet(
		      new CharArraySet(Arrays.asList(
		          "l", "m", "t", "qu", "n", "s", "j", "d", "c", "jusqu", "quoiqu", "lorsqu", "puisqu"), true));

	@Override
	protected TokenStreamComponents createComponents(String arg0) {
		Tokenizer tokenizer = new StandardTokenizer();
        TokenStream filter = new LowerCaseFilter(tokenizer);
        filter = new LowerCaseFilter(filter);
        filter = new StopFilter(filter, FRENSH_STOP_WORDS);
        filter = new ElisionFilter(filter, DEFAULT_ARTICLES);
        filter = new ASCIIFoldingFilter(filter);
        return new TokenStreamComponents(tokenizer, filter);
	}

}
