package yt.sehrschlecht.keepitems.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 2.0
 */
public class FilterManager {
    private static FilterManager instance;

    private List<ItemFilter> filters;

    public FilterManager() {
        instance = this;
        this.filters = new ArrayList<>();
    }

    public void registerFilter(ItemFilter filter) {
        this.filters.add(filter);
    }

    public void registerFilters(ItemFilter... filters) {
        this.filters.addAll(Arrays.asList(filters));
    }

    public static FilterManager getInstance() {
        return instance;
    }

    public List<ItemFilter> getFilters() {
        return filters;
    }
}
