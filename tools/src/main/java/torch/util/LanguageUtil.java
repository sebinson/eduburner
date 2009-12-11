package torch.util;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-6
 * Time: 19:58:59
 */
public class LanguageUtil {

    public static boolean isBasicLatin(char c) {
        return Character.UnicodeBlock.of(c) == Character.UnicodeBlock.BASIC_LATIN;
    }

    public static boolean isAsciiLetter(int codePoint) {
		return (codePoint >= 'A' && codePoint <= 'Z') || (codePoint >= 'a' && codePoint <= 'z');
	}

	public static boolean isRussiaLetter(int codePoint) {
		return (codePoint >= 'А' && codePoint <= 'я') || codePoint=='Ё' || codePoint=='ё';
	}

	public static boolean isGreeceLetter(int codePoint) {
		return (codePoint >= 'Α' && codePoint <= 'Ω') || (codePoint >= 'α' && codePoint <= 'ω');
	}

    public static boolean isCJKCharacter(char input) {
        return Character.UnicodeBlock.of(input)
                == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS;
    }

    public static boolean isChineseChar(char c){
         int i = (int) (c & 0xffff);
         if(i>=0x4e00 && i<=0x9fff ||
                 i>=0x3400 && i<=0x4dbf ||
                 i>=0xf900 && i<=0xfaff){
             return true;
         }
         return false;
    }

    public static boolean isChineseText(String summary){
        String txtSummary = HtmlUtil.htmlToPlainText(summary);
        int length = txtSummary.length();
        int cnCount = 0;
        for(int i=0; i<length; i++){
           if(isChineseChar(txtSummary.charAt(i))){
               cnCount ++;
           }
        }
        float ratio = (float)cnCount/length;
        if(length < 20 && ratio > 0.4f || length > 20 && ratio > 0.3f){
             return true;
        }
        return false;
    }

    public static void main(String... args){
        System.out.println(isChineseText("<a></a><href></href><div></div><div></div>测"));
    }
}
