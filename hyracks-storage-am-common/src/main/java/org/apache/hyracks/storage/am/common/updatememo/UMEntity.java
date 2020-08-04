package org.apache.hyracks.storage.am.common.updatememo;

public class UMEntity<T> {
	private T ts;
	private int cnt;

	public UMEntity(T ts){
		this.ts = ts;
		this.cnt = 1;
	}

	public int getCnt() {
		return this.cnt;
	}

	public void plusCnt(){
		this.cnt++;
	}

	public boolean minusCnt(){
		this.cnt--;
		if(this.cnt == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addCnt(int newCnt) {
		this.cnt += newCnt;
	}

	public T getTS() {
		return this.ts;
	}

	public void updateTS(T newTS) {
		this.ts = newTS;
	}

	public boolean newerThan(UMEntity<T> e){

		if((Integer)this.ts > (Integer)e.ts)
			return true;
		else
			return false;
	}

public boolean newerThan(T ts){

		if((Integer)this.ts > (Integer)ts)
			return true;
		else
			return false;

	}
}
