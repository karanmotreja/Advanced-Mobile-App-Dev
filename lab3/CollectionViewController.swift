//
//  CollectionViewController.swift
//  lab3
//
//  Created by Karan Motreja on 2/28/19.
//  Copyright © 2019 Karan Motreja. All rights reserved.
//

import UIKit

private let reuseIdentifier = "Cell"

class CollectionViewController: UICollectionViewController, UICollectionViewDelegateFlowLayout {

    var ninerImages=[String]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        for i in 1...12{
            ninerImages.append("49er" + String(i))
        }
        
        //enables large titles
        navigationController?.navigationBar.prefersLargeTitles = true
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using [segue destinationViewController].
        // Pass the selected object to the new view controller.
    }
    */

    // MARK: UICollectionViewDataSource

    override func numberOfSections(in collectionView: UICollectionView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of items
        return ninerImages.count
    }
    
    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: reuseIdentifier, for: indexPath) as! CollectionViewCell
        
        // Configure the cell
        let image = UIImage(named: ninerImages[indexPath.row])
        cell.imageView.image = image
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let image = UIImage(named: ninerImages[indexPath.row])
        
        //create new size
        let newSize = CGSize(width: (image?.size.width)!/40, height: (image?.size.height)!/40)
        
        
        UIGraphicsBeginImageContextWithOptions(newSize, false, 1.0)
        
        let rect = CGRect(x: 0, y: 0, width: newSize.width, height: newSize.height)
        image?.draw(in: rect)
        
        let image2 = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        //end resizing
        //returns size of resized image
        return (image2?.size)!
    }
    
    override func collectionView(_ collectionView: UICollectionView, viewForSupplementaryElementOfKind kind: String, at indexPath: IndexPath) -> UICollectionReusableView
    {
        
        var view: UICollectionReusableView?
        
        // Header
        if kind == UICollectionView.elementKindSectionHeader {
            let header = collectionView.dequeueReusableSupplementaryView(ofKind: kind, withReuseIdentifier: "Header", for: indexPath) as? CollectionSupplementaryView
            header?.headerLabel.text = "Home of the Faithful"
            view = header
        }
            
        // Footer
        else if kind == UICollectionView.elementKindSectionFooter {
            let footer = collectionView.dequeueReusableSupplementaryView(ofKind: kind, withReuseIdentifier: "Footer", for: indexPath) as? CollectionSupplementaryView
            footer?.footerLabel.text = "Quest for Six"
            view = footer
        }
        return view!
    
    }

    //UICollectionViewDelegateFlowLayout method
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets
    {
        //define margins for spacing
        let sectionInsets = UIEdgeInsets(top: 25.0, left: 10.0, bottom: 25.0, right: 10.0)
        return sectionInsets
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?)
    {
        if segue.identifier == "showDetail"{
            let indexPath = collectionView?.indexPath(for: sender as! CollectionViewCell)
            let detailVC = segue.destination as! DetailViewController
            detailVC.imageName = ninerImages[(indexPath?.row)!]
        }
    }
    
}
