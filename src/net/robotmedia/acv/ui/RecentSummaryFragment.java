package net.robotmedia.acv.ui;

import net.androidcomics.acv.R;
import net.robotmedia.acv.adapter.RecentListBaseAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;

public class RecentSummaryFragment extends SherlockFragment {

	protected ViewGroup mRecentItems = null;
	protected ListView mRecentItemsList = null;
	protected RecentListBaseAdapter mRecentItemsListAdapter = null;
	
	public RecentSummaryFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.recent_summary, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mRecentItems = (ViewGroup) getActivity().findViewById(R.id.main_recent);
		mRecentItemsList = (ListView) getActivity().findViewById(R.id.main_recent_list);
		mRecentItemsList.setEmptyView(getActivity().findViewById(R.id.main_recent_list_no_items));
		mRecentItemsListAdapter = new RecentListBaseAdapter(getActivity(), R.layout.recent_summary_item);
		mRecentItemsListAdapter.setMaxNumItems(2);
		mRecentItemsList.setAdapter(mRecentItemsListAdapter);
		mRecentItemsList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// String path = (String) parent.getItemAtPosition(position);
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mRecentItemsListAdapter.refresh();
	}
	
}