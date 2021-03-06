package com.bakdata.conquery.util.dict;

import static com.bakdata.conquery.util.dict.TTDirection.LEFT;
import static com.bakdata.conquery.util.dict.TTDirection.MIDDLE;
import static com.bakdata.conquery.util.dict.TTDirection.RIGHT;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BytesNode extends ABytesNode {

	@Getter
	protected final byte key;
	
	@Override
	public int get(byte[] k, int i) {
		byte c = k[i];
		if(c<key) {
			if(getLeft()==null) {
				return -1;
			}
			else {
				return getLeft().get(k, i);
			}
		}
		else if(c>key) {
			if(getRight()==null) {
				return -1;
			}
			else {
				return getRight().get(k, i);
			}
		}
		else {
			if(i+1==k.length) {
				return getValue();
			}
			else {
				if(getMiddle()==null) {
					return -1;
				}
				else {
					return getMiddle().get(k, i+1);
				}
			}
		}
	}
	
	@Override
	public ValueNode getNode(byte[] k, int i) {
		byte c = k[i];
		if(c<key) {
			if(getLeft()==null) {
				return null;
			}
			else {
				return getLeft().getNode(k, i);
			}
		}
		else if(c>key) {
			if(getRight()==null) {
				return null;
			}
			else {
				return getRight().getNode(k, i);
			}
		}
		else {
			if(i+1==k.length) {
				return getThisAsValuesNode();
			}
			else {
				if(getMiddle()==null) {
					return null;
				}
				else {
					return getMiddle().getNode(k, i+1);
				}
			}
		}
	}

	@Override
	public ValueNode getNearestNode(byte[] k, int i, ValueNode bestCandidate) {
		byte c = k[i];
		if(c<key) {
			if(getLeft()==null) {
				return bestCandidate;
			}
			else {
				return getLeft().getNearestNode(k, i, bestCandidate);
			}
		}
		else if(c>key) {
			if(getRight()==null) {
				return bestCandidate;
			}
			else {
				return getRight().getNearestNode(k, i, bestCandidate);
			}
		}
		else {
			if(i+1==k.length) {
				return getThisAsValuesNode();
			}
			else {
				if(getMiddle()==null) {
					return getThisAsValuesNode();
				}
				else {
					return getMiddle().getNearestNode(k, i+1, getThisAsValuesNode());
				}
			}
		}
	}

	@Override
	public int put(BytesTTMap map, NodeParent<ABytesNode> parent, TTDirection direction, byte[] k, int i, int value) {
		byte c = k[i];
		int result = -1;
		
		if(c<key) {
			if(getLeft()==null) {
				setLeft(TTHelper.createBytesValueNode(map, Arrays.copyOfRange(k, i, k.length), value));
			}
			else {
				result = getLeft().put(map, this, LEFT, k, i, value);
			}
		}
		else if(c>key) {
			if(getRight()==null) {
				setRight(TTHelper.createBytesValueNode(map, Arrays.copyOfRange(k, i, k.length), value));
			}
			else {
				result = getRight().put(map, this, RIGHT, k, i, value);
			}
		}
		else {
			if(i+1==k.length) {
				result = setValue(map, parent, direction, value);
			}
			else {
				if(getMiddle()==null) {
					setMiddle(TTHelper.createBytesValueNode(map, Arrays.copyOfRange(k, i+1, k.length), value));
				}
				else {
					result = getMiddle().put(map, this, MIDDLE, k, i+1, value);
				}
			}
		}
		
		added(result);
		return result;
	}
	
	public int getValue() {
		return -1;
	}
	
	public ValueNode getThisAsValuesNode() {
		return null;
	}
	
	protected int setValue(BytesTTMap map, NodeParent<ABytesNode> parent, TTDirection direction, int value) {
		BytesValueNode rep = new BytesValueNode(key, value);
		rep.copyChildrenFrom(this);
		parent.replace(this, direction, rep);
		map.setEntry(rep, value);
		return -1;
	}
	
	@Override
	protected int ownValue() {
		return 0;
	}
	
	@Override
	public String toString() {
		return Byte.toString(key);
	}

	@Override
	public byte[] key() {
		return new byte[] {key};
	}
}