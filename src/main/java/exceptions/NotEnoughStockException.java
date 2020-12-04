package exceptions;

//@ResponseStatus(value=HttpStatus.TEMPORARY_REDIRECT, reason="Not enough stock")
public class NotEnoughStockException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final int productId;

	public NotEnoughStockException(int id) {
		super("Out of stock");
		productId = id;		
	}

	public int getProductId(){
		return this.productId;
	}
	
}
