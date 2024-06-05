import React, { FormEvent, useRef, useState } from 'react';
import { FeatureModeEnum } from '../../../models/utils/FeatureModeEnum';

interface ProductModalProps {
  row: any;
  mode: string;
  categories: string[]; // Danh sách các category
  suppliers: string[]; // Danh sách các supplier
  closeModal: () => void;
  onSave: (data: any) => void;
}

export const ProductModal: React.FC<ProductModalProps> = ({ row, mode, categories, suppliers, closeModal, onSave }) => {
  const [selectedFiles, setSelectedFiles] = useState<File[]>([]);
  const [previewUrls, setPreviewUrls] = useState<string[]>([]);

  const productNameRef = useRef<HTMLInputElement>(null);
  const supplierRef = useRef<HTMLSelectElement>(null);
  const priceRef = useRef<HTMLInputElement>(null);
  const quantityRef = useRef<HTMLInputElement>(null);
  const categoryRef = useRef<HTMLSelectElement>(null);
  const unitRef = useRef<HTMLSelectElement>(null);
  const modeRef = useRef<HTMLSelectElement>(null);
  const descriptionRef = useRef<HTMLTextAreaElement>(null);

  const units = ['Piece', 'Dozen', 'Gross', 'Set', 'Pair'];
  console.log(row);


  const handleUploadImage = (event: React.ChangeEvent<HTMLInputElement>) => {
    const files = Array.from(event.target.files || []);
    setSelectedFiles(files);

    const fileUrls = files.map(file => URL.createObjectURL(file));
    setPreviewUrls(fileUrls);
  };

  const handleRemoveImage = (index: number) => {
    const newSelectedFiles = selectedFiles.filter((_, i) => i !== index);
    const newPreviewUrls = previewUrls.filter((_, i) => i !== index);

    setSelectedFiles(newSelectedFiles);
    setPreviewUrls(newPreviewUrls);
    updateInputFiles(newSelectedFiles);
  };

  const updateInputFiles = (files: File[]) => {
    const dataTransfer = new DataTransfer();
    files.forEach(file => dataTransfer.items.add(file));

    const fileInput = document.getElementById('multiple_files') as HTMLInputElement;
    fileInput.files = dataTransfer.files;
  };

  const handleSubmitConfirm = (event: FormEvent) => {
    event.preventDefault();
    const updatedData = {
      id: row.id,
      productName: productNameRef.current?.value,
      supplier: supplierRef.current?.value,
      price: priceRef.current?.value,
      category: categoryRef.current?.value,
      productDesc: descriptionRef.current?.value ?? '',
      unit: unitRef.current?.value,
      quantity: quantityRef.current?.value,
      featureMode: modeRef.current?.value,
      images: selectedFiles, // Assuming backend can handle the file uploads
    };
    console.log(updatedData);
    onSave(updatedData);
    closeModal();
  };

  return (
    <form id="updateProductModal" aria-hidden="true" onSubmit={handleSubmitConfirm}>
      <div className="bg-white max-w-screen-md rounded-lg shadow dark:bg-gray-700">
        <div className="flex justify-between items-start p-4 rounded-t border-b dark:border-gray-600">
          <h3 className="text-xl font-semibold text-gray-900 dark:text-white">{mode == "EDIT" ? 'Update Product' : 'Create Product'}</h3>
          <button
            type="button"
            className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-base p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-600 dark:hover:text-white"
            data-modal-toggle="updateProductModal"
            onClick={closeModal}
          >
            <svg aria-hidden="true" className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
              <path
                fillRule="evenodd"
                d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 011.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                clipRule="evenodd"
              />
            </svg>
            <span className="sr-only">Close modal</span>
          </button>
        </div>
        <div className="p-6 space-y-6">
          <div className="grid gap-4 mb-4 sm:grid-cols-2">
            <div>
              <label htmlFor="name" className="block mb-2 text-base font-medium text-gray-900 dark:text-white">Name</label>
              <input type="text" ref={productNameRef} name="name" id="name" defaultValue={row.productName} className="bg-gray-50 border border-gray-300 text-gray-900 text-base rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Ex. Apple iMac 27&ldquo;" required />
            </div>
            <div>
              <label htmlFor="supplier" className="block mb-2 text-base font-medium text-gray-900 dark:text-white">Supplier</label>
              <select ref={supplierRef} id="supplier" defaultValue={row.supplier} className="bg-gray-50 border border-gray-300 text-gray-900 text-base rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                {suppliers.map(supplier => (
                  <option key={supplier} value={supplier}>{supplier}</option>
                ))}
              </select>
            </div>
            <div>
              <label htmlFor="price" className="block mb-2 text-base font-medium text-gray-900 dark:text-white">Price</label>
              <input type="number" ref={priceRef} defaultValue={row.price} name="price" id="price" className="bg-gray-50 border border-gray-300 text-gray-900 text-base rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="$299" required />
            </div>
            <div>
              <label htmlFor="category" className="block mb-2 text-base font-medium text-gray-900 dark:text-white">Category</label>
              <select ref={categoryRef} id="category" defaultValue={row.category} className="bg-gray-50 border border-gray-300 text-gray-900 text-base rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                {categories.map(category => (
                  <option key={category} value={category || ''}>{category}</option>
                ))}
              </select>
            </div>
            <div>
              <label htmlFor="quantity" className="block mb-2 text-base font-medium text-gray-900 dark:text-white">Quantity</label>
              <input type="number" ref={quantityRef} defaultValue={row.price} name="price" id="price" className="bg-gray-50 border border-gray-300 text-gray-900 text-base rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="$299" required />
            </div>
            <div>
              <label htmlFor="unit" className="block mb-2 text-base font-medium text-gray-900 dark:text-white">Unit</label>
              <select ref={unitRef} id="category" defaultValue={row.unit} className="bg-gray-50 border border-gray-300 text-gray-900 text-base rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                {units.map(unit => (
                  <option key={unit} value={unit}>{unit}</option>
                ))}
              </select>
            </div>
            <div className="sm:col-span-2">
              <label htmlFor="mode" className="block mb-2 text-base font-medium text-gray-900 dark:text-white">Mode</label>
              <select ref={modeRef} id="category" defaultValue={row.featureMode} className="bg-gray-50 border border-gray-300 text-gray-900 text-base rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                {Object.keys(FeatureModeEnum).map(mode => (
                  <option key={mode} value={mode}>{mode}</option>
                ))}
              </select>
            </div>

            <div className="sm:col-span-2">
              <label className="block mb-2 text-base font-medium text-gray-900 dark:text-white" htmlFor="multiple_files">Upload multiple files</label>
              <input
                className="block w-full text-base text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 dark:text-gray-400 focus:outline-none dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400"
                id="multiple_files"
                type="file"
                multiple
                onChange={handleUploadImage}
                accept='image/png, image/jpeg'
              />
              <div className="flex flex-wrap mt-4">
                {previewUrls.map((url, index) => (
                  <div key={index} className="relative w-32 h-32 mr-2 mb-2">
                    <img src={url} alt={`Preview ${index}`} className="w-full h-full object-cover" />
                    <button
                      type="button"
                      onClick={() => handleRemoveImage(index)}
                      className="absolute top-0 right-0 bg-red-500 text-white px-2 py-0.5 rounded-full"
                    >
                      X
                    </button>
                  </div>
                ))}
              </div>
            </div>
            <div className="sm:col-span-2">
              <label htmlFor="description" className="block mb-2 text-base font-medium text-gray-900 dark:text-white">Description</label>
              <textarea ref={descriptionRef} id="description" defaultValue={row.productDesc} className="block p-2.5 w-full text-base text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Write a product description here..."></textarea>
            </div>
          </div>
        </div>
        <div className="flex items-center p-6 space-x-2 rounded-b border-t border-gray-200 dark:border-gray-600">
          <button type="submit" className="text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-base px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800">Save</button>

          <button type="button" onClick={closeModal} className="text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-base px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800">Cancel</button>
        </div>
      </div>
    </form>
  );
};
