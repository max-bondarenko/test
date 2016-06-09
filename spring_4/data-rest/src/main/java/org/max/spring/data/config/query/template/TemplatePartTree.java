package org.max.spring.data.config.query.template;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maksym_Bondarenko on 2/22/2016.
 */
public class TemplatePartTree implements Iterable<TemplatePartTree.OrPart> {

    private static final String AND_KEYWORD = "And";
    private static final String OR_KEYWORD = "Or";
    /*
     * We look for a pattern of: keyword followed by
     *
     *  an upper-case letter that has a lower-case variant \p{Lu}
     * OR
     *  any other letter NOT in the BASIC_LATIN Uni-code Block \\P{InBASIC_LATIN} (like Chinese, Korean, Japanese, etc.).
     *
     * @see http://www.regular-expressions.info/unicode.html
     * @see http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html#ubc
     */
    private static final String KEYWORD_TEMPLATE = "(%s)(?=(\\p{Lu}|\\P{InBASIC_LATIN}))";
    private static String methodPrefix[] = {"find", "query"};
    private static final Pattern PREFIX_TEMPLATE =
            Pattern.compile("^(" + StringUtils.arrayToDelimitedString(methodPrefix, "|") + ")(\\p{Lu}.*?)??(By|With)");
    /**
     * The subject, for example "findDistinctUserByNameOrderByAge" would have the predicate "NameOrderByAge".
     */
    private final Predicate predicate;

    private final Subject subject;

    /**
     * Creates a new {@link TemplatePartTree} by parsing the given {@link String}.
     *
     * @param source the {@link String} to parse
     */
    public TemplatePartTree(String source) {
        Assert.notNull(source, "Source must not be null");
        Matcher matcher = PREFIX_TEMPLATE.matcher(source);
        if (!matcher.find()) {
            this.predicate = new Predicate(StringUtils.capitalize(source));
            this.subject = null;
        } else {
            String group = matcher.group(0);
            subject = new Subject(group);
            this.predicate = new Predicate(source.substring(matcher.group().length()));
        }
    }

    /**
     * Splits the given text at the given keywords. Expects camel-case style to only match concrete keywords and not
     * derivatives of it.
     *
     * @param text    the text to split
     * @param keyword the keyword to split around
     * @return an array of split items
     */
    private static String[] split(String text, String keyword) {

        Pattern pattern = Pattern.compile(String.format(KEYWORD_TEMPLATE, keyword));
        return pattern.split(text);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<OrPart> iterator() {
        return predicate.iterator();
    }

    /**
     * Returns an {@link Iterable} of all parts contained in the {@link TemplatePartTree}.
     *
     * @return the iterable {@link TemplatePart}s
     */
    public Iterable<TemplatePart> getParts() {

        List<TemplatePart> result = new ArrayList<TemplatePart>();
        for (OrPart orPart : this) {
            for (TemplatePart part : orPart) {
                result.add(part);
            }
        }
        return result;
    }

    public List<String> getPropertyNames() {
        List<String> retVal = new LinkedList<>();
        for (TemplatePart part : getParts()) {
            retVal.add(part.getProperty());
        }
        return retVal;
    }

    public String getDataSource() {
        return subject == null ? null : subject.dsName;
    }

    /**
     * Returns all {@link TemplatePart}s of the {@link TemplatePartTree} of the given  Type.
     *
     * @param type
     * @return
     */
    public Iterable<TemplatePart> getParts(TemplatePart.Type type) {

        List<TemplatePart> result = new ArrayList<TemplatePart>();

        for (TemplatePart part : getParts()) {
            if (part.getType().equals(type)) {
                result.add(part);
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return String.format("%s", StringUtils.collectionToDelimitedString(predicate.nodes, " or "));
    }

    /**
     * A part of the parsed source that results from splitting up the resource around {@literal Or} keywords. Consists of
     * {@link TemplatePart}s that have to be concatenated by {@literal And}.
     */
    public static class OrPart implements Iterable<TemplatePart> {

        private final List<TemplatePart> children = new ArrayList<TemplatePart>();

        /**
         * Creates a new {@link OrPart}.
         *
         * @param source the source to split up into {@literal And} parts in turn.
         */
        OrPart(String source) {
            String[] split = split(source, AND_KEYWORD);
            for (String part : split) {
                if (StringUtils.hasText(part)) {
                    children.add(new TemplatePart(part));
                }
            }
        }

        public Iterator<TemplatePart> iterator() {
            return children.iterator();
        }

        @Override
        public String toString() {
            return StringUtils.collectionToDelimitedString(children, " and ");
        }
    }

    /**
     * Represents the predicate part of the query.
     *
     * @author Oliver Gierke
     * @author Phil Webb
     */
    private static class Predicate {

        private final List<OrPart> nodes = new ArrayList<OrPart>();

        public Predicate(String predicate) {
            String[] split = split(predicate, OR_KEYWORD);
            for (String part : split) {
                nodes.add(new OrPart(part));
            }
        }

        public Iterator<OrPart> iterator() {
            return nodes.iterator();
        }

    }

    private static class Subject {
        private static final Pattern DATA_SOURCE_TEMPLATE = Pattern.compile("^\\w+?In(\\p{Lu}.*?)??With");

        String dsName;

        public Subject(String predicate) {
            Matcher matcher = DATA_SOURCE_TEMPLATE.matcher(predicate);
            if (matcher.find()) {
                String group = matcher.group(1);
                if (StringUtils.isEmpty(group)) {
                    throw new IllegalArgumentException("Empty DataSource name");
                }
                dsName = StringUtils.uncapitalize(group);
            }
        }
    }

}
