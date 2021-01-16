package org.gugino.gamemanager.util;

public class ByteEncryptor {
	
	public static byte[][] encryptBytes(byte[][] _data, int _oddKey, int _evenKey) {
		byte[][] _encrypted = new byte[_data.length][_data.length];
		for(int i = 0; i < _data.length; i++) {
			for(int x = 0; x < _data.length; x++) {
				_encrypted[i][x] = (byte)((i % 2 == 0) ? _data[i][x] + _evenKey : _data[i][x] - _oddKey);
			}
		}
		return _encrypted;
	}
	
	public static byte[][] decryptBytes(byte[][] _data, int _oddKey, int _evenKey) {
		byte[][] _decrypted = new byte[_data.length][_data.length];
		for(int i = 0; i < _data.length; i++) {
			for(int x = 0; x < _data.length; x++) {
				_decrypted[i][x] = (byte)((i % 2 == 0) ? _data[i][x] - _evenKey : _data[i][x] + _oddKey);
			}
		}
		return _decrypted;
	}
	
	public static byte[] encryptBytes(byte[] _data, int _oddKey, int _evenKey) {
		byte[] _encrypted = new byte[_data.length];
		for(int i = 0; i < _data.length; i++) {
			_encrypted[i] = (byte)((i % 2 == 0) ? _data[i] + _evenKey : _data[i] - _oddKey);
		}
		return _encrypted;
	}
	
	public static byte[] decryptBytes(byte[] _data, int _oddKey, int _evenKey) {
		byte[] _decrypted = new byte[_data.length];
		for(int i = 0; i < _data.length; i++) {
			_decrypted[i] = (byte)((i % 2 == 0) ? _data[i] - _evenKey : _data[i] + _oddKey);
		}
		return _decrypted;
	}
}
