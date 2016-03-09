package org.max.spring.data.config.query.template;

/**
 * Created by Maksym_Bondarenko on 2/22/2016.
 */

import org.springframework.util.Assert;

import java.util.*;

/**
 * A single part of a method name that has to be transformed into a query part. The actual transformation is defined by
 * a {@link Type} that is determined from inspecting the given part. The query part can then be looked up via
 * {@link #getProperty()}.
 *
 * @author Oliver Gierke
 * @author Martin Baumgartner
 */
public class TemplatePart {

    private final String propertyPath;
    private final TemplatePart.Type type;

    /**
     * Creates a new {@link TemplatePart} from the given method name part, the {@link Class} the part originates from and the
     * start parameter index.
     *
     * @param source must not be {@literal null}.
     */
    public TemplatePart(String source) {
        Assert.hasText(source, "Part source must not be null or emtpy!");
        this.type = Type.fromProperty(source);
        this.propertyPath = type.extractProperty(source);
    }


    public boolean getParameterRequired() {

        return getNumberOfArguments() > 0;
    }

    /**
     * Returns how many method parameters are bound by this part.
     *
     * @return
     */
    public int getNumberOfArguments() {

        return type.getNumberOfArguments();
    }

    /**
     * @return the propertyPath
     */
    public String getProperty() {

        return propertyPath;
    }

    /**
     * @return the type
     */
    public TemplatePart.Type getType() {

        return type;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }

        TemplatePart that = (TemplatePart) obj;
        return this.propertyPath.equals(that.propertyPath) && this.type.equals(that.type);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        int result = 37;
        result += 17 * propertyPath.hashCode();
        result += 17 * type.hashCode();
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s %s", propertyPath, type);
    }

    /**
     * The type of a method name part. Used to create query parts in various ways.
     *
     * @author Oliver Gierke
     */
    public static enum Type {

        BETWEEN(2, "IsBetween", "Between"), IS_NOT_NULL(0, "IsNotNull", "NotNull"), IS_NULL(0, "IsNull", "Null"), LESS_THAN(
                "IsLessThan", "LessThan"), LESS_THAN_EQUAL("IsLessThanEqual", "LessThanEqual"), GREATER_THAN("IsGreaterThan",
                "GreaterThan"), GREATER_THAN_EQUAL("IsGreaterThanEqual", "GreaterThanEqual"), BEFORE("IsBefore", "Before"), AFTER(
                "IsAfter", "After"), NOT_LIKE("IsNotLike", "NotLike"), LIKE("IsLike", "Like"), STARTING_WITH("IsStartingWith",
                "StartingWith", "StartsWith"), ENDING_WITH("IsEndingWith", "EndingWith", "EndsWith"), CONTAINING(
                "IsContaining", "Containing", "Contains"), NOT_IN("IsNotIn", "NotIn"), IN("IsIn", "In"), NEAR("IsNear", "Near"), WITHIN(
                "IsWithin", "Within"), REGEX("MatchesRegex", "Matches", "Regex"), EXISTS(0, "Exists"), TRUE(0, "IsTrue", "True"), FALSE(
                0, "IsFalse", "False"), NEGATING_SIMPLE_PROPERTY("IsNot", "Not"), SIMPLE_PROPERTY("Is", "Equals");

        public static final Collection<String> ALL_KEYWORDS;

        // Need to list them again explicitly as the order is important
        // (esp. for IS_NULL, IS_NOT_NULL)
        private static final List<TemplatePart.Type> ALL = Arrays.asList(IS_NOT_NULL, IS_NULL, BETWEEN, LESS_THAN, LESS_THAN_EQUAL,
                GREATER_THAN, GREATER_THAN_EQUAL, BEFORE, AFTER, NOT_LIKE, LIKE, STARTING_WITH, ENDING_WITH, CONTAINING,
                NOT_IN, IN, NEAR, WITHIN, REGEX, EXISTS, TRUE, FALSE, NEGATING_SIMPLE_PROPERTY, SIMPLE_PROPERTY);
        private final List<String> keywords;
        private final int numberOfArguments;

        static {
            List<String> allKeywords = new ArrayList<String>();
            for (Type type : ALL) {
                allKeywords.addAll(type.keywords);
            }
            ALL_KEYWORDS = Collections.unmodifiableList(allKeywords);
        }

        /**
         * Creates a new {@link Type} using the given keyword, number of arguments to be bound and operator. Keyword and
         * operator can be {@literal null}.
         *
         * @param numberOfArguments
         * @param keywords
         */
        private Type(int numberOfArguments, String... keywords) {

            this.numberOfArguments = numberOfArguments;
            this.keywords = Arrays.asList(keywords);
        }

        private Type(String... keywords) {

            this(1, keywords);
        }

        /**
         * Returns the {@link Type} of the {@link TemplatePart} for the given raw propertyPath. This will try to detect e.g.
         * keywords contained in the raw propertyPath that trigger special query creation. Returns {@link #SIMPLE_PROPERTY}
         * by default.
         *
         * @param rawProperty
         * @return
         */
        public static TemplatePart.Type fromProperty(String rawProperty) {

            for (TemplatePart.Type type : ALL) {
                if (type.supports(rawProperty)) {
                    return type;
                }
            }

            return SIMPLE_PROPERTY;
        }

        /**
         * Returns all keywords supported by the current {@link Type}.
         *
         * @return
         */
        public Collection<String> getKeywords() {
            return Collections.unmodifiableList(keywords);
        }

        /**
         * Returns whether the the type supports the given raw property. Default implementation checks whether the property
         * ends with the registered keyword. Does not support the keyword if the property is a valid field as is.
         *
         * @param property
         * @return
         */
        protected boolean supports(String property) {

            if (keywords == null) {
                return true;
            }

            for (String keyword : keywords) {
                if (property.endsWith(keyword)) {
                    return true;
                }
            }

            return false;
        }

        /**
         * Returns the number of arguments the propertyPath binds. By default this exactly one argument.
         *
         * @return
         */
        public int getNumberOfArguments() {

            return numberOfArguments;
        }

        /**
         * Callback method to extract the actual propertyPath to be bound from the given part. Strips the keyword from the
         * part's end if available.
         *
         * @param part
         * @return
         */
        public String extractProperty(String part) {

//            String candidate = StringUtils.uncapitalize(part);
            String candidate = part;
            for (String keyword : keywords) {
                if (candidate.endsWith(keyword)) {
                    return candidate.substring(0, candidate.indexOf(keyword));
                }
            }
            return candidate;
        }
    }

}
