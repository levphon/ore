package org.oreframework.util.jar;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import gnu.regexp.RE;
import gnu.regexp.REException;


/**
 * Searches a set of directories and archives for files whose names match a given
 * regular expression.  The search may optionally recurse directories or archives.
 *
 * @author <a href="doug@dseifert.net">Doug Seifert</a>
 */
public class JarSearch {
    private Set mInitialFiles;
    private Set mFiles;
    private RE mSearchRE;
    private boolean mRecurseDirectories = false;
    private boolean mRecurseArchives = false;


    /**
     * Create a search object that will search a set of directories, files and archives
     * for files whose path names match the provided regular expression.  The list
     * may contain files, in which case the search is made against the file itelf.  The
     * list may also contain directories, in which case the directory and optionally all
     * it's contents are searched.  Finally, the list may contain jar archives.  By default,
     * only archive contents are searched.  Optionally, archives within archives (archive
     * recursion) may be searched.
     *
     * @param aSearchTerm A string search expression that is turned into a
     *         gnu.regexp.RE object by invoking the RE(Object) constructor. 
     * @param lDirectories The set of files, directories and archives to search.
     * @throws REException If the search term can't be turned into a valid RE object.
     */
    public JarSearch(String aSearchTerm, Set lDirectories) throws REException {
        mSearchRE = new RE(aSearchTerm);
        mInitialFiles = lDirectories;
    }

    /**
     * Create a search object that will search a set of directories, files and archives
     * for files whose path names match the provided regular expression.  The list
     * may contain files, in which case the search is made against the file itelf.  The
     * list may also contain directories, in which case the directory and optionally all
     * it's contents are searched.  Finally, the list may contain jar archives.  By default,
     * only archive contents are searched.  Optionally, archives within archives (archive
     * recursion) may be searched.
     *
     * @param aSearchRE A gnu.regexp.RE used to match file path names against.
     * @param lDirectories The set of files, directories and archives to search.
     */
    public JarSearch(RE aSearchRE, Set lDirectories) {
        mSearchRE = aSearchRE;
        mInitialFiles = lDirectories;
    }

    /**
     * The main program for the JarSearch class.  This program may be invoked as follows:
     * <code>java net.das.jarsearch.JarSearch [-r] [-a] {regexpstring} [file ...]</code>
     * <ul>
     * <li>If the -r flag is provided, any directories provided as arguments will be recursed.</li>
     * <li>If the -a flag is provided, any archives provided as arguments, or any archives found
     * as a result of directory recursion, will be recursed</li>
     * </ul>
     *
     * <p>
     * Archive recursion means that archives nested with archives to any level will be searched.
     * </p>
     * <p>
     * This method will call System.exit(0) if the search was performed without an error.  If an
     * error occurs (for example, because the regexpstring can't be parsed into a valide
     * gnu.regexp.RE object), System.exit(1) will be called.  System.exit(2)
     * will be called in the event of a usage error.
     * </p>
     * <p>
     * Example:<br>
     * java net.das.jarsearch.JarSearch -a Bean foo.ear<br>
     * Will list all files whose names contain the substring 'Bean' in the given ear archive.
     * Module archives that might be contained in the ear will also be searched.
     * </p>
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        
        if (args.length < 2) {
            System.err.println("Usage: java " + JarSearch.class + " [-r] [-a] <regexpstring> [file ...]");
            System.err.println("    <regexpstring> is a string that can be parsed into a "
                + "valid gnu.regexp.RE object.");
            System.err.println("    zero or more directories may be specified. If none are specified,"
                + " the current working directory is searched.");
            System.exit(2);
        }
        
        try {
            int lArgIndex = 0;
            boolean lRecurseDirs = false;
            boolean lRecurseArchives = false;
            while (args[lArgIndex].startsWith("-")) {
                if ("-a".equals(args[lArgIndex])) {
                    lRecurseArchives = true;
                } else if ("-r".equals(args[lArgIndex])) {
                    System.out.println("Arg is -r, recurse dirs = true");
                    lRecurseDirs = true;
                }
                lArgIndex++;
            }

            if (args.length - lArgIndex < 2) {
                System.err.println("Usage: java " + JarSearch.class + " [-r] [-a] <regexpstring> [file ...]");
                System.err.println("    <regexpstring> is a string that can be parsed into a "
                    + "valid gnu.regexp.RE object.");
                System.err.println("    zero or more directories may be specified. If none are specified,"
                    + " the current working directory is searched.");
                System.exit(2);
            }

            String lSearchTerm = args[lArgIndex++];
            
            Set lDirectories = new TreeSet();
            for (int i = lArgIndex; i < args.length; ++i) {
                lDirectories.add(new File(args[i]));
            }

            JarSearch lSearch = new JarSearch(lSearchTerm, lDirectories);
            lSearch.setRecurseArchives(lRecurseArchives);
            lSearch.setRecurseDirectories(lRecurseDirs);

            System.out.println("Searching for '" + lSearchTerm + "' ...");
            List lResults = lSearch.execute();

            System.out.println("There are " + lResults.size() + " matches:");
            Iterator i = lResults.iterator();
            while (i.hasNext()) {
                String lMatch = (String) i.next();
                System.out.println(lMatch);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }
   
    /**
     * Flag the search to recurse directories.
     *
     * @param aFlag Turn on or off directory recursion.
     */
    public void setRecurseDirectories(boolean aFlag) {
        mRecurseDirectories = aFlag;
    }

    /**
     * Flag the search to recurse archives.  If this is true,
     * archives within archives will be searched.
     *
     * @param aFlag Turn on or off archive recursion.
     */
    public void setRecurseArchives(boolean aFlag) {
        mRecurseArchives = aFlag;
    }

    /**
     * Perform the search and return a List of search results.  The result is a list
     * of String objects of the form:
     * <pre>
     * path/that/matched
     * path/of/archive/that/matched.jar
     * archive.jar>that/has/a/matching/file
     * an/archive.jar>within/an/archive.jar>that/has/a/match
     * ...
     * </pre>
     * @return A list of matches of the search expression
     */
    public List execute() {

        // Find all files first. Returns a list of all command line args
        // and their children, recursively, if the -r flag was provided
        mFiles = findAllFiles(mInitialFiles);

        List lMatches = new ArrayList();
        Iterator i = mFiles.iterator();
        while (i.hasNext()) {
            File lFile = (File) i.next();

            // Is the file an archive, perform an archive search
            if (isArchive(lFile.getName())) {
                try {
                    checkArchiveFile(lFile, lMatches);
                } catch (IOException ioe) {
                    System.out.println("Error checking archive: " + ioe);
                }
            } else {
                // It is a regular file, just match the path name against the RE
                if (mSearchRE.getMatch(lFile.getPath()) != null) {
                    lMatches.add(lFile.getPath());
                }
            }
        }
        return lMatches;
    }


    private void checkArchiveFile(File aFile, List aMatches)
        throws IOException {
        JarFile lJar = new JarFile(aFile);
        try {
            // Go through the jar entries looking for matches
            Enumeration lEntries = lJar.entries();
            while (lEntries.hasMoreElements()) {
                JarEntry lEntry = (JarEntry) lEntries.nextElement();
                checkEntry(lJar.getName(), lJar, lEntry, aMatches);      
            }
        } finally {
            lJar.close();
        }
    }
    
    private void checkArchiveStream(String aPrefix, JarInputStream aStream, List aMatches) throws IOException {
        //System.out.println("Checking stream: " + aPrefix);
        JarEntry lEntry = null;
        while ((lEntry = aStream.getNextJarEntry()) != null) {

            try {
                checkEntry(aPrefix, null, lEntry, aMatches);

                if (isArchive(lEntry.getName()) && mRecurseArchives) {
                    // We have an archive within an archive, read the data and create a new
                    // Jar input stream for it.  We don't want to close this stream, because
                    // it is a substream of a larger open enclosing stream.
                    JarInputStream lNewStream = new JarInputStream(aStream);
                    checkArchiveStream(aPrefix + ">" + lEntry.getName(), lNewStream, aMatches);
                } else {
                    // Just read and discard the data to get to the next entry
                    byte [] lBuf = new byte[4096];
                    while (aStream.read(lBuf, 0, 4096) > 0) {
                        // do nothing, throw away the data
                    }
                }
            } finally {
                aStream.closeEntry();
            }
        }     
    }
    
    private void checkEntry(String aPrefix, JarFile aOriginalFile, JarEntry aEntry, List aMatches) throws IOException {
        // If we are looking at an archive within a top-level (on the filesystem) archive,
        // open a stream and look inside it if the -a flag was specified.
        if (aOriginalFile != null && isArchive(aEntry.getName()) && mRecurseArchives) {
             JarInputStream lStream = new JarInputStream(aOriginalFile.getInputStream(aEntry));
             try {
                 checkArchiveStream(aPrefix + ">" + aEntry.getName(), lStream, aMatches);
             } finally {
                 lStream.close();
             }
        } else {
            if (mSearchRE.getMatch(aEntry.getName()) != null) {
                aMatches.add(aPrefix + ">" + aEntry.getName());
            }
        }
    }
    
    private boolean isArchive(String aName) {
        return (aName.toLowerCase().endsWith(".jar")
             || aName.toLowerCase().endsWith(".ear")
             || aName.toLowerCase().endsWith(".zip")
             || aName.toLowerCase().endsWith(".rar")
             || aName.toLowerCase().endsWith(".war"));
    }

    private Set findAllFiles(Set aBaseDirs) {
        // Find all the files in the input set, performs initial directory recursion
        Set lFiles = new TreeSet();
        Iterator i = aBaseDirs.iterator();
        while (i.hasNext()) {
            File lBaseDir = (File) i.next();
            if (lBaseDir.isDirectory() && mRecurseDirectories) {
                findRegularFiles(lBaseDir, lFiles);
                findArchives(lBaseDir, lFiles);
            }
            lFiles.add(lBaseDir);
        }
        return lFiles;
    }


    private void findArchives(File aDir, final Set aFiles) {
        File[] lArchiveFiles = aDir.listFiles(
            new FileFilter() {
                public boolean accept(File pathname) {
                    if (pathname.isDirectory()) {
                        findArchives(pathname, aFiles);
                        return false;
                    }
                    if (isArchive(pathname.getPath())) {
                        return true;
                    }
                    return false;
                }
            });
        if (lArchiveFiles != null) {
            for (int i = 0; i < lArchiveFiles.length; ++i) {
                aFiles.add(lArchiveFiles[i]);
            }
        }
    }


    private void findRegularFiles(File aDir, final Set aFiles) {
        File[] lRegularFiles = aDir.listFiles(
            new FileFilter() {
                public boolean accept(File pathname) {
                    if (pathname.isDirectory() && mRecurseDirectories) {
                        findRegularFiles(pathname, aFiles);
                        return false;
                    }
                    return true;
                }
            });
        if (lRegularFiles != null) {
            for (int i = 0; i < lRegularFiles.length; ++i) {
                aFiles.add(lRegularFiles[i]);
            }
        }
    }
}

