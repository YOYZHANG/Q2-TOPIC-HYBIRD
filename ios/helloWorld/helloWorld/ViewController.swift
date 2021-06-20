//
//  ViewController.swift
//  helloWorld
//
//  Created by zhangxiaoqian03 on 2021/6/20.
//

import UIKit
import WebKit

class ViewController: UIViewController, WKUIDelegate {
    
    var webView: WKWebView!
    
    override func loadView() {
        let webConfiguration = WKWebViewConfiguration()
        webView = WKWebView(frame:.zero, configuration: webConfiguration)
        webView.uiDelegate = self
        view = webView
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let myURL = URL(string: "https://www.baidu.com")
        let myRequest = URLRequest(url:myURL!)
        webView.load(myRequest)
        // Do any additional setup after loading the view.
    }
    
    @IBAction func showMessage(sender: UIButton) {
            let alertController = UIAlertController(title: "Welcome to My First App", message: "Hello World", preferredStyle: UIAlertController.Style.alert)
            alertController.addAction(UIAlertAction(title: "OK", style: UIAlertAction.Style.default, handler: nil))
            present(alertController, animated: true, completion: nil)
        }
}

