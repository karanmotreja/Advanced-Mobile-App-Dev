//
//  DetailViewController.swift
//  lab3
//
//  Created by Karan Motreja on 2/28/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import UIKit

class DetailViewController: UIViewController {

    @IBOutlet weak var imageView: UIImageView!
    var imageName : String?
    
    @IBAction func share(_ sender: UIBarButtonItem)
    {
        var imageArray = [UIImage]()
        imageArray.append(imageView.image!)
        
        let shareScreen = UIActivityViewController(activityItems: imageArray, applicationActivities: nil)
        
        shareScreen.modalPresentationStyle = .popover
        shareScreen.popoverPresentationController?.barButtonItem = sender
        
        present(shareScreen, animated: true, completion: nil)
        
    }
    // Populate the image view with the image the user selected every time the detail view appears.
    override func viewWillAppear(_ animated: Bool) {
        if let name = imageName {
            imageView.image = UIImage(named: name)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
