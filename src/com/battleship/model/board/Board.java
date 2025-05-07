package com.battleship.model.board;

import java.util.ArrayList;
import java.util.List;

import com.battleship.model.ship.Ship;

/**
 * Lớp "Board" biểu diễn một bàn chơi của trò chơi
 *
 * @author Nguyen Pham Hoang Mai
 * @version 1.0
 * @since 2025-04-27
 */

public class Board {

	// --- THUỘC TÍNH ---
	private static final int BOARD_SIZE = 10; // Kích thước của bảng
	private Node[][] board; // Mảng 2 chiều lưu trữ các ô trên bảng
	private List<Ship> ships; // Danh sách các tàu trên bảng

	// --- HÀM KHỞI TẠO ---
	/**
	 * Hàm khởi tạo với 2 tham số:
	 *
	 * @param board Mảng 2 chiều lưu trữ các ô trên bảng
	 * @param lship Danh sách các tàu trên bảng
	 */
	public Board() {
		this.board = new Node[BOARD_SIZE][BOARD_SIZE];
		this.ships = new ArrayList<>();
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = new Node(i, j);
			}
		}
	}

	// --- GETTER & SETTER ---
	/**
	 * Hàm lấy ô trên bảng
	 *
	 * @param x Tọa độ x của ô
	 * @param y Tọa độ y của ô
	 * @return ô trên bảng
	 */
	public Node getNode(int x, int y) {
		return board[x][y];
	}

	/**
	 * Hàm lấy mảng 2 chiều lưu trữ các ô trên bảng
	 *
	 * @return mảng 2 chiều lưu trữ các ô trên bảng
	 */
	public Node[][] getBoard() {
		return board;
	}

	/**
	 * Hàm lấy kích thước của bảng
	 *
	 * @return kích thước của bảng
	 */
	public int getBoardSize() {
		return BOARD_SIZE;
	}

	// --- CÁC PHƯƠNG THỨC KHÁC ---
	/**
	 * Hàm thêm tàu vào danh sách tàu
	 *
	 * @param ship Tàu cần thêm
	 */
	public void addShip(Ship ship) {
		ships.add(ship);
	}

	public Ship addShip(int x, int y, int length, boolean isHorizontal) {
	    List<Node> shipNodes = new ArrayList<>();
	    if (isHorizontal) {
	        for (int i = 0; i < length; i++) {
	            shipNodes.add(getNode(x, y + i));
	        }
	    } else {
	        for (int i = 0; i < length; i++) {
	            shipNodes.add(getNode(x + i, y));
	        }
	    }
	    for (Node node : shipNodes) node.setHasShip(true);
	    Ship ship = new Ship(length, shipNodes, isHorizontal);
	    ships.add(ship);
	    return ship;
	}

	public List<Ship> getShips() {
	    return ships;
	}

	/**
	 * Hàm kiểm tra tất cả tàu đã chìm hết chưa
	 *
	 * @return true nếu tất cả tàu đã chìm hết, false nếu không
	 */
	public boolean allShipsSunk() {
		for (Ship ship : ships) {
			if (!ship.isSunk()) {
				return false;
			}
		}

		return true;
	}
	
	public void removeShip(Ship ship) {
	    if (ship == null) return;
	    // Xóa trạng thái có tàu trên các node
	    for (Node node : ship.getNodes()) {
	        node.setHasShip(false);
	    }
	    // Xóa tàu khỏi danh sách
	    ships.remove(ship);
	}
	
	// Trong Board.java
	public boolean isShipSunkAt(int x, int y) {
	    for (Ship ship : ships) {
	        for (Node node : ship.getNodes()) {
	            if (node.getX() == x && node.getY() == y) {
	                return ship.isSunk();
	            }
	        }
	    }
	    return false;
	}
	
	/**
	 * Hàm kiểm tra tọa độ có hợp lệ hay không
	 *
	 * @param x Tọa độ x của ô
	 * @param y Tọa độ y của ô
	 * @return true nếu tọa độ có hợp lệ, false nếu không
	 */
	public boolean isValidCoordinate(int x, int y) {
		return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
	}
}
