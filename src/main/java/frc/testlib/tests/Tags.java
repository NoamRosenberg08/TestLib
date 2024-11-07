package frc.testlib.tests;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Tags {

    private final String[] tags;

    public Tags(String... tags) {
        this.tags = tags;
    }

    public String[] getTags() {
        return tags;
    }

    public boolean containsTag(String targetTag) {
        for (String tag : tags) {
            if (tag.equals(targetTag)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsTags(String... targetTags){
        for (String targetTag : targetTags) {
            if(containsTag(targetTag)){
                return true;
            }
        }
        return false;
    }

    public boolean containsTags(Tags targetTags){
        return containsTags(targetTags.getTags());
    }

    @Override
    public String toString() {
        return "Tags{" +
                "tags=" + Arrays.toString(tags) +
                '}';
    }


}
