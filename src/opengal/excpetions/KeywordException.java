package opengal.excpetions;

import opengal.syntax.Keyword;

public class KeywordException extends RuntimeException {
    public KeywordException(Keyword keyword) {
        super(keyword.getKeywordName());
    }
}
