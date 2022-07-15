package yt.sehrschlecht.keepitems.filters;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 2.0
 *
 * The filter manager is used to register and manage all item filters.
 */
public class FilterManager {
    private static FilterManager instance;

    private List<ItemFilter> filters;

    public FilterManager() {
        instance = this;
        this.filters = new ArrayList<>();
    }

    /**
     * @param filter the filter to register
     * this method registers a filter to the filter manager.
     */
    public void registerFilter(ItemFilter filter) {
        this.filters.add(filter);
    }

    /**
     * @param filters the filters to register
     * This method is used to register multiple filters at once.
     */
    public void registerFilters(ItemFilter... filters) {
        this.filters.addAll(Arrays.asList(filters));
    }

    /**
     * @return An instance of the filter manager.
     */
    @Nullable
    public static FilterManager getInstance() {
        return instance;
    }

    public List<ItemFilter> getFilters() {
        return filters;
    }
}
