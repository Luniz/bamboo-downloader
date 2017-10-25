
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.util;

//~--- non-JDK imports --------------------------------------------------------

import android.app.Activity;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//~--- JDK imports ------------------------------------------------------------

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Adapter for lists where only a single view type is used
 *
 * @param <V>
 */
public abstract class SingleTypeAdapter<V> extends TypeAdapter {
    private static final Object[] EMPTY = new Object[0];
    private final LayoutInflater  inflater;
    private final int             layout;
    private final int[]           children;
    private Object[]              items;

    /**
     * Create adapter
     *
     * @param activity
     * @param layoutResourceId
     */
    public SingleTypeAdapter(final Activity activity, final int layoutResourceId) {
        this(activity.getLayoutInflater(), layoutResourceId);
    }

    /**
     * Create adapter
     *
     * @param context
     * @param layoutResourceId
     */
    public SingleTypeAdapter(final Context context, final int layoutResourceId) {
        this(LayoutInflater.from(context), layoutResourceId);
    }

    /**
     * Create adapter
     *
     * @param inflater
     * @param layoutResourceId
     */
    public SingleTypeAdapter(final LayoutInflater inflater, final int layoutResourceId) {
        this.inflater = inflater;
        this.layout   = layoutResourceId;
        items         = EMPTY;

        int[] childIds = getChildViewIds();

        if (childIds == null) {
            childIds = new int[0];
        }

        children = childIds;
    }

    /**
     * Get a list of all items
     *
     * @return list of all items
     */
    @SuppressWarnings("unchecked")
    protected List<V> getItems() {
        List<? extends Object> objList = Arrays.asList(items);

        return (List<V>) objList;
    }

    /**
     * Set items to display
     *
     * @param items
     */
    public void setItems(final Collection<?> items) {
        if ((items != null) &&!items.isEmpty()) {
            setItems(items.toArray());
        } else {
            setItems(EMPTY);
        }
    }

    /**
     * Set items to display
     *
     * @param items
     */
    public void setItems(final Object[] items) {
        if (items != null) {
            this.items = items;
        } else {
            this.items = EMPTY;
        }

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @SuppressWarnings("unchecked")
    public V getItem(final int position) {
        return (V) items[position];
    }

    @Override
    public long getItemId(final int position) {
        return items[position].hashCode();
    }

    /**
     * Get child view ids to store
     * <p/>
     * The index of each id in the returned array should be used when using the
     * helpers to update a specific child view
     *
     * @return ids
     */
    protected abstract int[] getChildViewIds();

    /**
     * Initialize view
     *
     * @param view
     * @return view
     */
    protected View initialize(final View view) {
        return super.initialize(view, children);
    }

    /**
     * Update view for item
     *
     * @param position
     * @param view
     * @param item
     */
    protected void update(int position, View view, V item) {
        setCurrentView(view);
        update(position, item);
    }

    /**
     * Update item
     *
     * @param position
     * @param item
     */
    protected abstract void update(int position, V item);

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = initialize(inflater.inflate(layout, null));
        }

        update(position, convertView, getItem(position));

        return convertView;
    }
}