package org.apache.hyracks.storage.am.common.updatememo;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateMemo<T> {
	private HashMap<T, UMEntity<T>> um;
	private static final Logger LOGGER = LogManager.getLogger();

	public UpdateMemo(){
		this.um = new HashMap<T, UMEntity<T>>();
	}

	public void insertOrUpdate(T key, UMEntity<T> newEntity) {
		if(this.um.containsKey(key)) {
			if(this.um.get(key).newerThan(newEntity)) {
				this.um.get(key).updateTS((T)newEntity.getTS());
			}

			this.um.get(key).addCnt(newEntity.getCnt());
		}
		else {
			this.um.put(key, newEntity);
		}

	}

	public void insertOrUpdate(T key, T ts){
		if(this.um.containsKey(key)){
			// update entity
			this.um.get(key).updateTS(ts);
			this.um.get(key).plusCnt();
		}
		else{
			// put new entity
			this.um.put(key, new UMEntity<T>(ts));
		}
	}

	//	return true if the tuple with key needs to be cleaned
	public boolean cleanEntity(T key, T ts) {
		if(this.hasNewerEntity(key, ts)) {
			if(this.um.get(key).minusCnt()) {
				this.um.remove(key);

			}
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasKey(T key) {
		if(this.um.containsKey(key)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasNewerEntity(T key, T ts) {
		if(this.um.containsKey(key)) {
			if(this.um.get(key).newerThan(ts)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	public Set<Entry<T, UMEntity<T>>> getEntrySet(){
		return this.um.entrySet();
	}

	public int getSize() {
		return this.um.size();
	}

	public void reset() {
		this.um = new HashMap<T, UMEntity<T>>();
	}
}
