package torch.util;

import java.util.regex.Pattern;

/**
 * Convert provided html formatted string to text format.
 *
 *
 */
public final class HtmlUtil {

  /**
   * Regular expression to match html line breaks or paragraph tags
   * and adjacent whitespace
   */
  private static final Pattern htmlNewlinePattern =
    Pattern.compile("\\s*<(br|/?p)>\\s*");

  /** Regular expression to match list tags and adjacent whitespace */
  private static final Pattern htmlListPattern =
    Pattern.compile("\\s*<li>\\s*");

  /** Regular expression to match any remaining html tags */
  private static final Pattern htmlTagPattern =
    Pattern.compile("</?([^<]*)>");

  /** Maximum length of a line in email body (in characters) */
  public static final int EMAIL_LINE_WIDTH_MAX = 72;

  // This class should not be instantiated, hence the private constructor
  private HtmlUtil() {}

    /**
   * Convert provided html string to plain text preserving the formatting
   * as much as possible. Ensure line wrapping to 72 chars as default.
   * NOTE: add support for more HTML tags here.
   * For the present, convert &lt;br&gt; to '\n'
   *                  convert &lt;p&gt; and &lt;/p&gt; to '\n'
   *                  convert &lt;li&gt; to "\n- "
   * @throws NullPointerException
   */
  public static String htmlToPlainText(String html) {

    if (html == null) {
      throw new NullPointerException("Html parameter may not be null.");
    }

    // Clear any html indentation and incidental whitespace
    String text = StringUtil.stripAndCollapse(html);

    /*
     * Replace <br> and <p> tags with new line characters.
     * Replace <li> tags (HTML bullets) with dashes.
     * Remove any remaining HTML tags not supported yet.
     * Finally replace any HTML escape string with appropriate character
     */
    text = htmlNewlinePattern.matcher(text).replaceAll("\n");
    text = htmlListPattern.matcher(text).replaceAll("\n- ");
    text = htmlTagPattern.matcher(text).replaceAll("");
    text = StringUtil.unescapeHTML(text).trim();

    /*
     * Ensure no line of plain text is longer than default (72 chars)
     * NOTE: Use String.split, NOT StringUtil.split, in order to preserve
     * consecutive newline characters originating from <br> and <p> tags
     */
    return StringUtil.fixedWidth(text.split("\n"), EMAIL_LINE_WIDTH_MAX);
  }
}

