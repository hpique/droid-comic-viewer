/*******************************************************************************
 * Copyright 2009 Robot Media SL
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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