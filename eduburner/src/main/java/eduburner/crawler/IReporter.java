/* Reporter
 * 
 * Created on May 5, 2005
 *
 * Copyright (C) 2005 Internet Archive.
 * 
 * This file is part of the Heritrix web crawler (crawler.archive.org).
 * 
 * Heritrix is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * any later version.
 * 
 * Heritrix is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License
 * along with Heritrix; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package eduburner.crawler;


/**
 * @author stack
 * @version $Date: 2006-09-20 22:40:21 +0000 (Wed, 20 Sep 2006) $, $Revision: 4644 $
 */
public interface IReporter {
    /**
     * Get an array of report names offered by this Reporter. 
     * A name in brackets indicates a free-form String, 
     * in accordance with the informal description inside
     * the brackets, may yield a useful report.
     * 
     * @return String array of report names, empty if there is only
     * one report type
     */
    public String[] getReports();
    
    /**
     * Return a short single-line summary report as a String.
     * 
     * @return String single-line summary report
     */
    public String singleLineReport();
    
}
