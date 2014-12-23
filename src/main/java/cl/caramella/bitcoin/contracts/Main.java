package cl.caramella.bitcoin.contracts;

import java.io.File;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.*;

public class Main {
	
	public static void main(String args[]) throws Exception{
		
		String network="aaatestnet";
		String address="1K1BwXtUr2XtiLhQRMF7Piuzupk8fGy5si";
		NetworkParameters params;
		String filePrefix;
		
		if (network.equals("testnet")) {
		    params = TestNet3Params.get();
		    filePrefix = "forwarding-service-testnet";
		} else if (network.equals("regtest")) {
		    params = RegTestParams.get();
		    filePrefix = "forwarding-service-regtest";
		} else {
		    params = MainNetParams.get();
		    filePrefix = "forwarding-service";
		}
		
		
		Wallet wallet = new Wallet(params);
		Address address_=wallet.freshReceiveAddress();
		
		wallet.currentReceiveKey().getPrivKey();
		wallet.currentReceiveKey().getPubKeyHash();
		
		while(){
			
		}
		
		
		Address forwardingAddress = new Address(params, address);
		
		WalletAppKit kit = new WalletAppKit(params, new File("./work/"), filePrefix) {
		    @Override
		    protected void onSetupCompleted() {
		        // This is called in a background thread after startAndWait is called, as setting up various objects
		        // can do disk and network IO that may cause UI jank/stuttering in wallet apps if it were to be done
		        // on the main thread.
		        if (wallet().getKeychainSize() < 1)
		            wallet().importKey(new ECKey());
		    }
		};

		if (params == RegTestParams.get()) {
		    // Regression test mode is designed for testing and development only, so there's no public network for it.
		    // If you pick this mode, you're expected to be running a local "bitcoind -regtest" instance.
		    kit.connectToLocalHost();
		}

		// Download the block chain and wait until it's done.
		kit.startAndWait();
		
		
	}
	

}
